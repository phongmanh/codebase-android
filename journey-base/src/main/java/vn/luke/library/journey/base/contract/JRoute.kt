package vn.luke.library.journey.base.contract

interface JRoute {

    fun showDefaultError(error: String? = null)

    fun showDefaultMessage(message: String)

    fun showDefaultInfoPopup(message: String)

}