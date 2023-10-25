package vn.liam.codebase.base.networking

import java.net.UnknownHostException

object ErrorHandler {

    fun <T> handingError(throwable: Throwable): Resource<T> {
        return when (throwable) {
            is UnknownHostException -> Resource.Error("Internet connection is failed.")
            else -> Resource.Error("Unknown Error!")
        }
    }

}