package vn.luke.library.journey.base.scope.journey

import androidx.activity.OnBackPressedCallback
import vn.luke.library.journey.base.contract.JRoute
import vn.luke.library.journey.base.scope.micro.MicroBottomSheetFragment
import vn.luke.library.journey.base.scope.micro.MicroDialogFragment
import vn.luke.library.journey.base.scope.micro.MicroFragment

abstract class JourneyFragment<Route : JRoute>(
    override val navHostViewId: Int,
    layout: Int
) : MicroFragment<Route>(layout), IJourneyBackPress<Route> {
    constructor(navHostViewId: Int) : this(navHostViewId, 0)

    override val microHandleBackPressed: Lazy<OnBackPressedCallback> =
        lazy { newMicroHandleBackPressed() }

    override var shouldFinishMainActivityOnBackPressedCallback: Boolean = false
    override var shouldDeliverPopScreenToParentOnBackPressedCallback: Boolean = true

}

abstract class JourneyDialogFragment<Route : JRoute>(
    override val navHostViewId: Int,
    layout: Int,
) : MicroDialogFragment<Route>(layout), IJourneyBackPress<Route> {
    constructor(navHostViewId: Int) : this(navHostViewId, 0)

    override val microHandleBackPressed: Lazy<OnBackPressedCallback> =
        lazy { newMicroHandleBackPressed() }

    override var shouldFinishMainActivityOnBackPressedCallback: Boolean = false
    override var shouldDeliverPopScreenToParentOnBackPressedCallback: Boolean = true
}

abstract class JourneyBottomSheetFragment<Route : JRoute>(
    override val navHostViewId: Int,
    layout: Int,
) : MicroBottomSheetFragment<Route>(layout), IJourneyBackPress<Route> {
    constructor(navHostViewId: Int) : this(navHostViewId, 0)

    override val microHandleBackPressed: Lazy<OnBackPressedCallback> =
        lazy { newMicroHandleBackPressed() }

    override var shouldFinishMainActivityOnBackPressedCallback: Boolean = false
    override var shouldDeliverPopScreenToParentOnBackPressedCallback: Boolean = true
}