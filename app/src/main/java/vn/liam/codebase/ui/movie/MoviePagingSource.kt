package vn.liam.codebase.ui.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.repositories.MovieRepository
import java.io.IOException

class MoviePagingSource(private val movieServices: MovieRepository, private val query: String?) :
    PagingSource<Int, MovieModel>() {
    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        try {
            val pageIndex = params.key ?: 1
            val response = if (!query.isNullOrBlank()) {
                movieServices.searchMovies(
                    query,
                    pageIndex,
                    MovieSdk.PARAMS.DEFAULT_PAGE_SIZE
                )
            } else {
                movieServices.getTrendingMovies(
                    pageIndex,
                    MovieSdk.PARAMS.DEFAULT_PAGE_SIZE
                )
            }

//            if (response is Resource.Error) {
//                return LoadResult.Error(Throwable(response.message))
//            }
            val prevKey = if (pageIndex == 1) null else pageIndex - 1
            var nextKey: Int? = null
            var dataResult: List<MovieModel> = emptyList()
            response.data?.let {
                dataResult = it
                nextKey =
                    if (it.size < MovieSdk.PARAMS.DEFAULT_PAGE_SIZE || it.isEmpty()) null else pageIndex + 1
            }
            return LoadResult.Page(
                data = dataResult,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }


}