package com.sprinter.flux.flux.main

import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.Middleware
import com.sprinter.fluxlib.ReceiveAction
import io.reactivex.Observable
import timber.log.Timber

class CrashReportMiddleware : Middleware<GlobalState>() {

    override fun interceptor(
        action: Action<BaseData>,
        state: GlobalState,
        observable: Observable<ReceiveAction<Action<BaseData>, GlobalState>>
    ): Observable<ReceiveAction<Action<BaseData>, GlobalState>> {
        return observable
            .map { result ->
                if (result.action.data is IsErrorAction) {
                    val data = result.action.data as IsErrorAction
                    Timber.e(data.getThrowable(), "Handled exception")
                }

                result
            }
            .doOnError { error -> Timber.e(error, "Unhandled exception") }
            .onErrorReturn { ReceiveAction(action, state) }
    }
}
