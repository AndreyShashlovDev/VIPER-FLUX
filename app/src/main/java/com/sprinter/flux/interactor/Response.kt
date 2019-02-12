package com.sprinter.flux.interactor

data class Response<T>(val data: T? = null, val error: Throwable? = null)
