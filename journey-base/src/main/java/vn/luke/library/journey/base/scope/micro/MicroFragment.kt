package vn.luke.library.journey.base.scope.micro

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.luke.library.journey.base.contract.JRoute

abstract class MicroFragment<Route : JRoute>(
    private val layout: Int
) : Fragment(), IMicro<Route>, IMicroLifeCycleEvents {
    constructor() : this(0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout > 0) {
            return inflater.inflate(layout, container, false)
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onMicroCreated()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onMicroViewCreated()
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
        super.onPause()
        onMicroPaused()
    }

    override fun onStop() {
        super.onStop()
        onMicroStopped()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onMicroViewDestroyed()
    }

    override fun onDestroy() {
        super.onDestroy()
        onMicroDestroyed()
    }

}

abstract class MicroDialogFragment<Route : JRoute>(
    private val layout: Int
) : DialogFragment(), IMicro<Route>, IMicroLifeCycleEvents {
    constructor() : this(0)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout > 0) {
            return inflater.inflate(layout, container, false)
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onMicroCreated()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onMicroViewCreated()
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
        super.onPause()
        onMicroPaused()
    }

    override fun onStop() {
        super.onStop()
        onMicroStopped()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onMicroViewDestroyed()
    }

    override fun onDestroy() {
        super.onDestroy()
        onMicroDestroyed()
    }

}

abstract class MicroBottomSheetFragment<Route : JRoute>(
    private val layout: Int
) : BottomSheetDialogFragment(), IMicro<Route>, IMicroLifeCycleEvents {
    constructor() : this(0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout > 0) {
            return inflater.inflate(layout, container, false)
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onMicroCreated()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onMicroViewCreated()
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
        super.onPause()
        onMicroPaused()
    }

    override fun onStop() {
        super.onStop()
        onMicroStopped()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onMicroViewDestroyed()
    }

    override fun onDestroy() {
        super.onDestroy()
        onMicroDestroyed()
    }

}