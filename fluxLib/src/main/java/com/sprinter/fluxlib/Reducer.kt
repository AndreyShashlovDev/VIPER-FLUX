package com.sprinter.fluxlib

import io.reactivex.Observable

abstract class Reducer<S : State, D : BaseData> {

    abstract fun execute(store: ConfigurableStore<S>, state: S, action: Action<D>):
            Observable<ReceiveAction<Action<BaseData>, S>>

    abstract fun isServiceAction(action: Action<D>): Boolean
}