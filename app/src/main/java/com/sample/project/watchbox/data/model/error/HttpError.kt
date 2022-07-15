package com.sample.project.watchbox.data.model.error

import retrofit2.HttpException
import javax.net.ssl.SSLException

data class HttpError(
    var errorType: String? = null,
    var message: String? = null,
    var retryAction: (() -> Unit)? = null
) {
    constructor(
        throwable: Throwable,
        retryAction: (() -> Unit)? = null
    ) : this(retryAction = retryAction) {
        when (throwable) {
            //Certificate pinning exception, when main and second certificates expired or weird
            is SSLException -> {
                errorType = "SSLException"
                message = throwable.message.toString()
            }
            // Server
            is HttpException -> {
                errorType = "HttpException"
                message = throwable.message.toString()
            }
            else -> {
                errorType = "GenericException"
                message = throwable.message.toString()
            }
        }
    }
}
