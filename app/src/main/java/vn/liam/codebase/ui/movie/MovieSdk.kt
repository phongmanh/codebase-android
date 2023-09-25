package vn.liam.codebase.ui.movie

import androidx.core.os.bundleOf

object MovieSdk {

    object PARAMS {
        const val DEFAULT_PAGE_SIZE = 15
        const val SEARCH_QUERY_KEY = "SEARCH"
        const val MOVIE_ID_KEY = "MOVIE_ID"
        const val CACHED_TIMEOUT = 5L
        fun build(movieId: Int?) = bundleOf(MOVIE_ID_KEY to movieId)

    }

}