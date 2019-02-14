package com.sprinter.fluxlib

import io.reactivex.Observable

data class ReceiveAction<T : Action<BaseData>, S>(
    val action: T,
    val state: S
) {
    companion object {
        fun <S> createEmpty(state: S): Observable<ReceiveAction<Action<BaseData>, S>> {
            return Observable.just(ReceiveAction(Action<BaseData>(EmptyBaseData), state))
        }
    }
}
