package vn.liam.codebase.base.database.dao

import androidx.room.Dao
import androidx.room.Query
import vn.liam.codebase.base.models.MovieDetailsCached
import vn.liam.codebase.base.models.MovieModel

@Dao
interface DetailsCachedDAO: IDao<MovieDetailsCached>  {

    @Query("SELECT movie FROM detailscached WHERE movieId =:movieId")
    fun getMovieById(movieId: Int?): MovieModel?

}