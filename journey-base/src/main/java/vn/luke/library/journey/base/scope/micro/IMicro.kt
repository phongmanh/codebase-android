package vn.luke.library.journey.base.scope.micro

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import vn.luke.library.journey.base.contract.JRoute
import vn.luke.library.journey.base.scope.support.JMicroSupport

interface IMicro<Route : JRoute> : JMicroSupport<Route> {

    override val route: Route? get() = findRoute()

}

private fun <Route : JRoute> IMicro<Route>.findRoute(): Route? {
    (this as? Activity)?.let {
        Log.e("Lu", "cannot find route for Activity-${this::class.java.simpleName}")
        return null
    }
    (this as? Fragment)?.let { fragment ->
        //has parent
        //look for navhost
        //look for any fragment that implement the route
        var noloop = true
        var walker: Fragment? = fragment
        do {
            walker = walker?.parentFragment
            walker?.let { w ->
                noloop = false
                if (w is NavHostFragment) {
                    //look for next only
                    if (w.parentFragment == null) {
                        //navhost under activity
                        return w.findRouteForOrphanFragment("Fragment-${this::class.java.simpleName} under NavHost-under-Activity")
                    }
                    //parent of navhost MUST handle JRoute
                    return w.castParentAsRoute("Fragment-${this::class.java.simpleName} under Parent-of-NavHost-must-handle-JRoute")
                }
                //maybe, somehow this fragment handle his children's route
                w.castThisAsRoute<Route>()?.let {
                    return it
                }
            }
        } while (walker != null)
        //walker always null
        if (noloop) {
            //this fragment has no parent
            return fragment.findRouteForOrphanFragment("Orphan-Fragment-${this::class.java.simpleName}")
        }
        //somehow no one handle JRoute in the fragment hierarchy (even Activity)
        return fragment.findRouteForOrphanFragment("Fragment-${this::class.java.simpleName} under Noone-handle-JRoute-in-component-hierarchy")
    }
    Log.e("Lujou", "Cannot find route for Unknown-${this::class.java.simpleName}")
    return null
}

private fun <Route : JRoute> Fragment.findRouteForOrphanFragment(log: String): Route? {
    this.activity?.let { activity ->//check for activity
        (activity as? Route)?.let {
            return it
        }
    }
    Log.e("Lujou", "Cannot find route for $log")
    return null
}

private fun <Route : JRoute> Fragment.castParentAsRoute(log: String? = null): Route? {
    parentFragment?.let { fragment ->
        (fragment as? Route)?.let {
            return fragment
        }
    }
    log?.let {
        Log.e("Lujou", "Cannot find route for $log")
    }
    return null
}

private fun <Route : JRoute> Fragment.castThisAsRoute(log: String? = null): Route? {
    (this as? Route)?.let {
        return this
    }
    log?.let {
        Log.e("Lujou", "Cannot find route for $log")
    }
    return null
}