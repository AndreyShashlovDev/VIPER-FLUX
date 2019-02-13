package com.sprinter.flux.flux.main

import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.Middleware
import io.reactivex.Observable
import timber.log.Timber

class CrashReportMiddleware : Middleware<GlobalState>() {

    override fun interceptor(
        action: Action<BaseData>,
        state: GlobalState,
        observable: Observable<*>
    ): Observable<*> {
        return observable.doOnError { error -> Timber.e(error, "ALARM!!!!!") }
            .onExceptionResumeNext(Observable.empty())
    }
}
