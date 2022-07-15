package com.sample.project.watchbox.data.model

import com.sample.project.watchbox.data.model.error.HttpError

sealed class DataWrapper<out T>

data class Success<out T>(val data: T) : DataWrapper<T>()
class InitialSuccess<out T> : DataWrapper<T>()
class EmptySuccess<out T> : DataWrapper<T>()
data class Failure<out T>(val httpError: HttpError) : DataWrapper<T>()
data class Loading<out T>(val loading: Boolean) : DataWrapper<T>()
