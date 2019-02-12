package com.sprinter.flux.repository.source.network.exceptions

open class ApiException(mErrorCode: Int, mErrorMessage: String?) :
    RuntimeException("$mErrorCode: $mErrorMessage")
