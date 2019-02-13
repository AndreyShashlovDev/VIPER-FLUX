package com.sprinter.fluxlib

import io.reactivex.Observable

abstract class Reducer<S : State, D : BaseData> {

    abstract fun execute(store: MutableStore<S>, state: S, action: Action<D>): Observable<*>

    abstract fun isServiceAction(action: Action<D>): Boolean
}