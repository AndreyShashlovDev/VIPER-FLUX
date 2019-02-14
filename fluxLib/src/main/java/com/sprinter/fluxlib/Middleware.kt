package com.sprinter.fluxlib

import io.reactivex.Observable

abstract class Middleware<S : State> {

    abstract fun interceptor(
        action: Action<BaseData>,
        state: S,
        observable: Observable<ReceiveAction<Action<BaseData>, S>>
    ): Observable<ReceiveAction<Action<BaseData>, S>>
}
