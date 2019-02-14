package com.sprinter.flux.flux.main.middleware

import com.sprinter.flux.flux.main.GlobalState
import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.Middleware
import com.sprinter.fluxlib.ReceiveAction
import io.reactivex.Observable
import timber.log.Timber

class LoggingMiddleware : Middleware<GlobalState>() {

    override fun interceptor(
        action: Action<BaseData>,
        state: GlobalState,
        observable: Observable<ReceiveAction<Action<BaseData>, GlobalState>>
    ): Observable<ReceiveAction<Action<BaseData>, GlobalState>> {
        Timber.d("FLUX-> action: ${action.data}; state: $state")

        return observable
    }
}
