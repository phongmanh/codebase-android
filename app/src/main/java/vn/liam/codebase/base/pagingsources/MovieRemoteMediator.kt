package vn.liam.codebase.base.pagingsources

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import vn.liam.codebase.base.database.AppDatabase
import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.models.RemoteKey
import vn.liam.codebase.base.repositories.MovieRepository
import vn.liam.codebase.ui.movie.MovieSdk
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieRepository: MovieRepository,
    private val movieDb: AppDatabase
) : RemoteMediator<Int, MovieModel>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieModel>
    ): MediatorResult {

        return try {
            val currentPage: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyForClosestToCurrentPosition(state)
                    remoteKey?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    remoteKey?.preKey
                        ?: return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    remoteKey?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                }
            }

            val res =
                movieRepository.getTrendingMovies(currentPage, MovieSdk.PARAMS.DEFAULT_PAGE_SIZE)

            val endOfPaginationReached = res.data.isNullOrEmpty()
            val prePage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            res.data?.let { data ->
                movieDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDb.remoteKeyDao().deleteAll()
                        movieDb.movieDao().deleteAll()
                    }
                    val keys = data.map { movie ->
                        RemoteKey(
                            movie.movieId,
                            prePage,
                            nextPage,
                            System.currentTimeMillis()
                        )
                    }
                    movieDb.remoteKeyDao().insert(*keys.toTypedArray())
                    movieDb.movieDao().insertOrUpdate(*data.toTypedArray())
                }

                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } ?: return MediatorResult.Error(Throwable(res.message))


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout =
            TimeUnit.MILLISECONDS.convert(MovieSdk.PARAMS.CACHED_TIMEOUT, TimeUnit.MINUTES)
        return if (System.currentTimeMillis() - (movieDb.remoteKeyDao().getLatestUpdated()
                ?: 0) <= cacheTimeout
        ) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyForClosestToCurrentPosition(state: PagingState<Int, MovieModel>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { movieId ->
                movieDb.withTransaction {
                    movieDb.remoteKeyDao().getRemoteKey(movieId)
                }
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieModel>): RemoteKey? {
        return state.pages.firstOrNull()?.data?.firstOrNull()?.let {
            movieDb.withTransaction {
                movieDb.remoteKeyDao().getRemoteKey(it.movieId!!)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieModel>): RemoteKey? {
        return state.pages.lastOrNull()?.data?.lastOrNull()?.let { movie ->
            movieDb.withTransaction {
                movieDb.remoteKeyDao().getRemoteKey(movie.movieId!!)
            }
        }
    }

}