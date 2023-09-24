package vn.liam.codebase.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.networking.Resource
import vn.liam.codebase.base.repositories.MovieRepository
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    var searchQuery = stateHandle.getLiveData<String?>(MovieSdk.PARAMS.SEARCH_QUERY_KEY)

    val trendingMovie: Flow<PagingData<MovieModel>> = Pager(
        PagingConfig(
            pageSize = MovieSdk.PARAMS.DEFAULT_PAGE_SIZE,
            prefetchDistance = 2
        ), initialKey = 1
    ) {
        MoviePagingSource(movieRepository, searchQuery.value)
    }.flow.cachedIn(viewModelScope)


    fun setSearchQuery(query: String?) {
        searchQuery.value = query
    }

}