package com.sprinter.fluxlib

import io.reactivex.Observable

interface Store<S : State> {

    fun dispatch(action: Action<BaseData>)

    fun getState(): S

    fun subscribeToAction(): Observable<DefaultStore.ReceiveAction<Action<BaseData>, S>>
}
