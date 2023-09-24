package vn.liam.codebase.base.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import vn.liam.codebase.R

data class MovieModel(
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