package vn.liam.codebase.ui.movie

import vn.liam.codebase.base.models.MovieModel

interface MovieRoute {

    fun onMovieSelected(movie: MovieModel)

}