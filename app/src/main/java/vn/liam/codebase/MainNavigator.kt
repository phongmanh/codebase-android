package vn.liam.codebase

import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.main.MainRoute
import vn.liam.codebase.ui.movie.MovieRoute
import vn.liam.codebase.ui.movie.MovieSdk
import vn.liam.codebase.ui.movie.details.DetailsRoute
import vn.luke.library.journey.base.scope.journey.JourneyActivity
import vn.luke.library.journey.base.scope.journey.JourneyFragment

abstract class MainNavigator : JourneyActivity<MainRoute>(
    R.id.mainJourney, R.layout.main_journey
), MovieRoute, DetailsRoute {

    override fun onMovieSelected(movie: MovieModel) {
        nextScreen(R.id.action_to_movie_details, MovieSdk.PARAMS.build(movie.movieId))
    }

    override fun onDetailsGoBack() {
        popScreen()
    }
}