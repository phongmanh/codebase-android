package vn.luke.library.journey.base.scope.support

import vn.luke.library.journey.base.contract.JMicro
import vn.luke.library.journey.base.contract.JRoute

interface JMicroSupport<Route : JRoute> : JMicro<Route> {

    fun showError(error: String? = null) = route?.showDefaultError(error) ?: Unit

    fun showMessage(message: String) = route?.showDefaultMessage(message) ?: Unit

    fun showDefaultInfoPopup(message: String) = route?.showDefaultInfoPopup(message) ?: Unit

}