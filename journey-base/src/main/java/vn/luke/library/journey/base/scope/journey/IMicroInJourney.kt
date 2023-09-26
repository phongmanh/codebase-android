package vn.luke.library.journey.base.scope.journey

import android.os.Bundle
import androidx.fragment.app.Fragment
import vn.luke.library.journey.base.contract.JRoute
import vn.luke.library.journey.base.scope.micro.IMicro

interface IMicroInJourney<Route : JRoute> : IMicro<Route> {

    val journeyArguments: Bundle? get() = findArguments()

}

private fun <Route : JRoute> IMicro<Route>.findArguments(): Bundle? {
    val r = route ?: return null
    if (r is Fragment && r is IJourney<*>) {
        return r.arguments
    }
    return null
}