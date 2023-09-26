package vn.luke.library.journey.base.scope.journey

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import vn.luke.library.journey.base.contract.JRoute
import vn.luke.library.journey.base.scope.micro.IMicroLifeCycleEvents

interface IJourneyBackPress<Route : JRoute> : IJourney<Route>, IMicroLifeCycleEvents {

    override fun onMicroCreated() {
        microHandleRegisterBackPressed()
    }

    override fun onMicroDestroyed() {
        microHandleRemoveBackPressed()
    }

    val microHandleBackPressed: Lazy<OnBackPressedCallback>
    fun newMicroHandleBackPressed(): OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onMicroBackPressed()
        }
    }

    fun microHandleRegisterBackPressed() {
        if (this is Fragment) {
            requireActivity().onBackPressedDispatcher.addCallback(this, microHandleBackPressed.value)
        } else if (this is FragmentActivity) {
            onBackPressedDispatcher.addCallback(this, microHandleBackPressed.value)
        }
    }

    fun microHandleRemoveBackPressed() {
        microHandleBackPressed.value.remove()
    }

    fun onMicroBackPressed(): Boolean {
        popScreen()
        return true
    }

}