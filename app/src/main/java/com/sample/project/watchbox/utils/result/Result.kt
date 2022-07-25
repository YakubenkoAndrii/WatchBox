package com.sample.project.watchbox.utils.result

import com.sample.project.watchbox.data.model.error.HttpError

sealed class Result<T>(val data: T? = null, val httpError: HttpError? = null) {
    class Success<T>(data: T?): Result<T>(data)
    class Error<T>(httpError: HttpError, data: T? = null): Result<T>(data, httpError)
}