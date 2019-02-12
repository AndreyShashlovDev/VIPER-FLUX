package com.sprinter.fluxlib

abstract class Reducer<S : State, D : BaseData> {

    abstract fun execute(store: MutableStore<S>, state: S, action: Action<D>)

    abstract fun isServiceAction(action: Action<D>): Boolean
}