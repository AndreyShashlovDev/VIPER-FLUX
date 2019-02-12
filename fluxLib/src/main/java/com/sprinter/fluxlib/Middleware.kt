package com.sprinter.fluxlib

abstract class Middleware<S : State> {

    abstract fun interceptor(action: Action<BaseData>, state: S)
}
