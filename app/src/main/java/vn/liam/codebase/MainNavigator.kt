package vn.liam.codebase

import vn.liam.codebase.main.MainRoute
import vn.luke.library.journey.base.scope.journey.JourneyActivity

abstract class MainNavigator : JourneyActivity<MainRoute>(
    R.id.mainJourney, R.layout.main_journey
) {

}