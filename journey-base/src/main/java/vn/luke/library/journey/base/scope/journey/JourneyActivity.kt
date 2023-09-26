package vn.luke.library.journey.base.scope.journey

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import vn.luke.library.journey.base.contract.JRoute

abstract class JourneyActivity<Route : JRoute>(
    override val navHostViewId: Int,
    private val layout: Int = 0,
) : AppCompatActivity(), IJourneyBackPress<Route> {

    override val microHandleBackPressed: Lazy<OnBackPressedCallback> =
        lazy { newMicroHandleBackPressed() }

    override var shouldFinishMainActivityOnBackPressedCallback: Boolean = false
    override var shouldDeliverPopScreenToParentOnBackPressedCallback: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateSetContentView()
        onMicroViewCreated()
        onMicroCreated()
    }


    protected open fun onCreateSetContentView() {
        if (layout > 0) {
            setContentView(layout)
        }
    }

    override fun onStart() {
        super.onStart()
        onMicroStarted()
    }

    override fun onResume() {
        super.onResume()
        onMicroResumed()
    }

    override fun onPause() {
        onMicroPaused()
        super.onPause()
    }

    override fun onStop() {
        onMicroStopped()
        super.onStop()
    }

    override fun onDestroy() {
        onMicroViewDestroyed()
        onMicroDestroyed()
        super.onDestroy()
    }

}