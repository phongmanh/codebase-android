package vn.liam.codebase.base.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import vn.liam.codebase.R

@Entity(tableName = "movies")
data class MovieModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val movieId: Int?,
    @SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val release_date: String?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>?,
    @SerializedName("media_type")
    val media_type: String?,
    @SerializedName("original_title")
    val original_title: String?,
    @SerializedName("original_language")
    val original_language: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("backdrop_path")
    val backdrop_path: String?,
    @SerializedName("popularity")
    val popularity: Float?,
    @SerializedName("vote_count")
    val vote_count: Int?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val vote_average: Float?,
    //Details
    @SerializedName("genres")
    val genres: List<Genres>?,
    @SerializedName("production_companies")
    val production_companies: List<ProductionCompany>?,
    @SerializedName("production_countries")
    val production_countries: List<ProductionCountry>?,
    @SerializedName("spoken_languages")
    val spoken_languages: List<SpokenLanguage>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("status")
    val status: MovieStatus?, //Allowed Values: Rumored, Planned, In Production, Post Production, Released, Canceled
    @SerializedName("tagline")
    val tagline: String?
) {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, url: String?) {
            try {
                url?.let {
                    Glide.with(imageView.context).load(url).centerCrop()
                        .placeholder(R.drawable.image_placeholder_black_36)
                        .into(imageView)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Transient
    var imagePosterUrl: String = ""
}

enum class MovieStatus {
    Rumored, Planned, InProduction, PostProduction, Released, Canceled;
}

data class ProductionCompany(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val company_id: Int,
    @SerializedName("logo_path")
    val logo_path: String?,
    @SerializedName("origin_country")
    val origin_country: String
)

data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso_3166_1: String,
    @SerializedName("name") val name: String
)

data class SpokenLanguage(
    @SerializedName("iso_639_1") val iso_639_1: String,
    @SerializedName("name") val name: String
)


data class Genres(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("imdb_id")
    val imdb_id: String?
)