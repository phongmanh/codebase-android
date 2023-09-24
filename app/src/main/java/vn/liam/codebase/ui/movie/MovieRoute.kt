package vn.liam.codebase.ui.movie

import vn.liam.codebase.base.models.MovieModel
import vn.luke.library.journey.base.contract.JRoute

interface MovieRoute : JRoute {

    fun onMovieSelected(movie: MovieModel)

}