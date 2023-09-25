package vn.liam.codebase.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import vn.liam.codebase.base.database.AppDatabase
import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.pagingsources.MoviePagingSource
import vn.liam.codebase.base.pagingsources.MovieRemoteMediator
import vn.liam.codebase.base.repositories.MovieRepository
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val stateHandle: SavedStateHandle,
    private val appDb: AppDatabase
) : ViewModel() {

    var searchQuery = stateHandle.getLiveData<String?>(MovieSdk.PARAMS.SEARCH_QUERY_KEY)

    @OptIn(ExperimentalPagingApi::class)
    private fun getTrendingMovieFlow(): Flow<PagingData<MovieModel>> = Pager(
        config = PagingConfig(
            pageSize = MovieSdk.PARAMS.DEFAULT_PAGE_SIZE,
            prefetchDistance = 2
        ), initialKey = 1,
        remoteMediator = MovieRemoteMediator(movieRepository, appDb),
        pagingSourceFactory = { appDb.movieDao().getMovies() }
    ).flow.cachedIn(viewModelScope)


    private fun getSearchFlow(): Flow<PagingData<MovieModel>> = Pager(
        config = PagingConfig(
            pageSize = MovieSdk.PARAMS.DEFAULT_PAGE_SIZE,
            prefetchDistance = 2
        ), initialKey = 1,
        pagingSourceFactory = { MoviePagingSource(movieRepository, searchQuery.value) }
    ).flow.cachedIn(viewModelScope)

    fun getMovies(): Flow<PagingData<MovieModel>> =
        if (searchQuery.value.isNullOrBlank()) getTrendingMovieFlow() else getSearchFlow()

    fun setSearchQuery(query: String?) {
        searchQuery.value = query
    }

}