package com.sprinter.flux.flux.main

import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.Middleware
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class DelayMiddleware : Middleware<GlobalState>() {

    override fun interceptor(
        action: Action<BaseData>,
        state: GlobalState,
        observable: Observable<*>
    ): Observable<*> {
        return Observable.timer(15, TimeUnit.SECONDS)
            .flatMap { observable }
    }
}
