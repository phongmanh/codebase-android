package vn.liam.codebase.base.models

import com.google.gson.annotations.SerializedName

data class MovieRes(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int,
    @SerializedName("results")
    val results: List<MovieModel>?
)
