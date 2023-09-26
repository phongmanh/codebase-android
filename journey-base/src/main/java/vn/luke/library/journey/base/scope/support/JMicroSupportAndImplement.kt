package vn.luke.library.journey.base.scope.support

import vn.luke.library.journey.base.contract.JRoute

interface JMicroSupportAndImplement<Route : JRoute> : JMicroSupport<Route>, JRoute {

    override fun showError(error: String?) = showDefaultError(error)

    override fun showMessage(message: String) = showDefaultMessage(message)

    @Deprecated("Do not use this, but could override", ReplaceWith("showError"))
    override fun showDefaultError(error: String?) = route?.showDefaultError(error) ?: Unit

    @Deprecated("Do not use this, but could override", ReplaceWith("showMessage"))
    override fun showDefaultMessage(message: String) = route?.showDefaultMessage(message) ?: Unit

    override fun showDefaultInfoPopup(message: String) = route?.showDefaultInfoPopup(message) ?: Unit

}