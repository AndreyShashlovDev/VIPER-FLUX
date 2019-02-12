package com.sprinter.flux.flux.main

import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.Middleware
import timber.log.Timber

class LoggingMiddleware : Middleware<GlobalState>() {

    override fun interceptor(action: Action<BaseData>, state: GlobalState) {
        Timber.d("FLUX-> action: ${action.data}; state: $state")
    }
}
