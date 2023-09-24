package vn.liam.codebase.base.services

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vn.liam.codebase.base.di.IService
import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.models.MovieRes

interface MovieServices : IService {

    // time_window: day or week
    @GET("/3/trending/movie/day")
    suspend fun trendingMovies(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): MovieRes

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("query") query: String?, @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): MovieRes

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movie_id: Int): MovieModel

}