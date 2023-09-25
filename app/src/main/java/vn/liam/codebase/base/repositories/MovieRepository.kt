package vn.liam.codebase.base.repositories

import vn.liam.codebase.base.database.dao.DetailsCachedDAO
import vn.liam.codebase.base.models.MovieDetailsCached
import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.networking.ErrorHandler
import vn.liam.codebase.base.networking.Resource
import vn.liam.codebase.base.services.MovieServices
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieServices: MovieServices,
    private val detailsCached: DetailsCachedDAO
) {

    suspend fun getTrendingMovies(
        page: Int?,
        pageSize: Int
    ): Resource<List<MovieModel>> {
        return try {
            val res = movieServices.trendingMovies(page, pageSize)
            Resource.Success(res.results!!)
        } catch (e: Exception) {
            ErrorHandler.handingError(e)
        }
    }

    suspend fun searchMovies(
        query: String?,
        page: Int,
        pageSize: Int
    ): Resource<List<MovieModel>> {
        return try {
            val res = movieServices.searchMovie(query, page, pageSize)
            Resource.Success(res.results!!)
        } catch (e: Exception) {
            ErrorHandler.handingError(e)
        }
    }

    suspend fun getMovieById(movieId: Int): Resource<MovieModel> {
        return try {
            val res = movieServices.getMovieById(movieId)
            val cached = MovieDetailsCached(res.movieId, res)
            detailsCached.insert(cached)
            Resource.Success(res)
        } catch (e: Exception) {
            val resLocal = detailsCached.getMovieById(movieId)
            return if (resLocal != null) Resource.Success(resLocal)
            else ErrorHandler.handingError(e)
        }
    }
}