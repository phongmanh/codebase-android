package vn.liam.codebase.ui.movie.details

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.networking.Resource
import vn.liam.codebase.base.repositories.MovieRepository
import vn.liam.codebase.ui.movie.MovieSdk
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var movieId: Int? = savedStateHandle[MovieSdk.PARAMS.MOVIE_ID_KEY]

    fun getMovieById(movieId: Int) = liveData {
        emit(Resource.Loading())
        val res = withContext(Dispatchers.IO) {
            movieRepository.getMovieById(movieId)
        }
        emit(res)
    }

    fun saveArgs(args: Bundle?) {
        movieId = args?.getInt(MovieSdk.PARAMS.MOVIE_ID_KEY)
        savedStateHandle[MovieSdk.PARAMS.MOVIE_ID_KEY] = movieId
    }

}