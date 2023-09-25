package vn.liam.codebase.base.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailscached")
data class MovieDetailsCached(
    @PrimaryKey(autoGenerate = false)
    val movieId: Int?,
    val movie: MovieModel
)