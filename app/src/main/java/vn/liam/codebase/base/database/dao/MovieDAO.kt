package vn.liam.codebase.base.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import vn.liam.codebase.base.models.MovieModel

@Dao
interface MovieDAO : IDao<MovieModel> {

    @Query("SELECT * FROM movies WHERE movieId =:movieId")
    fun getMovieById(movieId: Int?): MovieModel?

    @Query("SELECT * FROM movies")
    fun getMovies(): PagingSource<Int, MovieModel>

    @Query("DELETE  FROM movies")
    fun deleteAll()

}