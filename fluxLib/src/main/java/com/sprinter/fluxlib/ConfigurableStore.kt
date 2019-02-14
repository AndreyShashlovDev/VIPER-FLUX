package com.sprinter.fluxlib

interface ConfigurableStore<S : State> : Store<S> {

    fun registerReducer(reducer: Reducer<S, out BaseData>): ConfigurableStore<S>

    fun registerMiddleware(middleware: Middleware<S>): ConfigurableStore<S>
}
