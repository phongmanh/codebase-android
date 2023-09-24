package vn.liam.codebase.base.repositories

import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.networking.Resource
import vn.liam.codebase.base.services.MovieServices
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieServices: MovieServices) {

    suspend fun getTrendingMovies(
        page: Int,
        pageSize: Int
    ): Resource<List<MovieModel>> {
        return try {
            val res = movieServices.trendingMovies(page, pageSize)
            Resource.Success(res.results!!)
        } catch (e: Exception) {
            Resource.Error(errorMessage = "Wrong")
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
            Resource.Error(errorMessage = "Wrong")
        }
    }
}