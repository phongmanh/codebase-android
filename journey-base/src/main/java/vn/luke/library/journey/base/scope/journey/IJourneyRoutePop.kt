package vn.luke.library.journey.base.scope.journey

import vn.luke.library.journey.base.contract.JRoute

interface IJourneyRoutePop: JRoute {
    fun onPopScreenRequest(): Boolean
}