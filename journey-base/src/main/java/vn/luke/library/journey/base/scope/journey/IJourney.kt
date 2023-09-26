package vn.luke.library.journey.base.scope.journey

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import vn.luke.library.journey.base.contract.JRoute
import vn.luke.library.journey.base.scope.support.JMicroSupportAndImplement

interface IJourney<Route : JRoute> : IMicroInJourney<Route>, JMicroSupportAndImplement<Route>,
    IJourneyRoutePop {

    var shouldFinishMainActivityOnBackPressedCallback: Boolean
    var shouldDeliverPopScreenToParentOnBackPressedCallback: Boolean

    val navHostViewId: Int

    fun findNavController(): NavController? = findNav()?.navController

    fun nextScreen(action: Int, args: Bundle? = null) = doNextScreen(action, args)

    fun popScreen(): Boolean {
        if (doPopScreen()) return true
        if (shouldFinishMainActivityOnBackPressedCallback && letFinishActivityIfAny()) return true
        if (shouldDeliverPopScreenToParentOnBackPressedCallback && letDeliverPopScreenToParentIfAny()) return true
        return false
    }

    override fun onPopScreenRequest(): Boolean = popScreen()

    override val journeyArguments: Bundle? get() = findArguments()

    fun justRun(justRun: () -> Unit) {
        justRun()
    }

}

private fun IJourney<*>.findArguments(): Bundle? {
    if (this is Fragment) return arguments
    return null
}

private fun IJourney<*>.doNextScreen(action: Int, args: Bundle?) {
    findNavController()?.navigate(action, args)
}

private fun IJourney<*>.doPopScreen(): Boolean {
    //findNavController()?.popBackStack()
    findNavController()?.let { n ->
        n.previousBackStackEntry?.let {
            n.currentBackStackEntry?.let {
                if (n.popBackStack()) return true
                n.previousBackStackEntry
                n.navigate(it.destination.id)//reload here
            }
        }
    }
    return false
}

private fun IJourney<*>.findNav(): NavHostFragment? {
    (this as? Activity)?.let { activity ->
        (activity as? FragmentActivity)?.let {
            activity.supportFragmentManager.findFragmentById(navHostViewId)?.let { fragment ->
                (fragment as? NavHostFragment)?.let {
                    return it
                }
            }
            Log.e("Lujou", "Cannot find NavHost for Activity-${this::class.java.simpleName}")
            return null
        }
        @Suppress("DEPRECATION") activity.fragmentManager.findFragmentById(navHostViewId)
            ?.let { fragment ->
                @Suppress("CAST_NEVER_SUCCEEDS") (fragment as? NavHostFragment)?.let {
                    return it
                }
            }
        Log.e(
            "Lujou", "Cannot find NavHost for Non-FragmentActivity-${this::class.java.simpleName}"
        )
        return null
    }
    (this as? Fragment)?.let { _ ->
        childFragmentManager.findFragmentById(navHostViewId)?.let { fragment ->
            (fragment as? NavHostFragment)?.let {
                return it
            }
        }
        Log.e("Lujou", "Cannot find NavHost for Fragment-${this::class.java.simpleName}")
        return null
    }
    Log.e("Lujou", "Cannot find NavHost for Unknown-${this::class.java.simpleName}")
    return null
}


private fun IJourney<*>.letFinishActivityIfAny(extras: Bundle? = null): Boolean {
    val activity = when (this) {
        is Fragment -> activity
        is Activity -> this
        else -> null
    }
    activity?.let { a ->
        val data = Intent()
        extras?.let { data.putExtras(it) }
        a.setResult(Activity.RESULT_OK, data)
        a.finish()
        return true
    }
    return false
}

private fun IJourney<*>.letDeliverPopScreenToParentIfAny(extras: Bundle? = null): Boolean {
    if (route is IJourneyRoutePop) (route as IJourneyRoutePop).onPopScreenRequest()
    return false
}
