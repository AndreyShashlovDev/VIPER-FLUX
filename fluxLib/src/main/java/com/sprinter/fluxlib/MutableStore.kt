package com.sprinter.fluxlib

interface MutableStore<S : State> : Store<S> {

    fun updateState(updatedState: S)

    fun registerReducer(reducer: Reducer<S, out BaseData>): MutableStore<S>

    fun registerMiddleware(middleware: Middleware<S>): MutableStore<S>
}
