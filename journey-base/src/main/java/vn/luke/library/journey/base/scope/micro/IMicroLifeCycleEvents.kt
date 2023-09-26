package vn.luke.library.journey.base.scope.micro

interface IMicroLifeCycleEvents {

    fun onMicroCreated() = Unit
    fun onMicroViewCreated() = Unit
    fun onMicroStarted() = Unit
    fun onMicroResumed() = Unit
    fun onMicroPaused() = Unit
    fun onMicroStopped() = Unit
    fun onMicroViewDestroyed() = Unit
    fun onMicroDestroyed() = Unit

}