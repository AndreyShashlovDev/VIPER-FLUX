package com.sprinter.flux.interactor

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

abstract class AbstractInteractor<Result, Parameter> {

    companion object {
        private val SCHEDULER_SINGLE_THREAD = Schedulers.from(
            Executors.newSingleThreadExecutor()
        )
    }

    private val subscription: CompositeDisposable = CompositeDisposable()

    abstract fun buildObservable(parameter: Parameter): Observable<Result>

    fun execute(parameter: Parameter, subscriber: Consumer<Result>) {
        subscription.add(
            buildObservable(parameter)
                .subscribeOn(SCHEDULER_SINGLE_THREAD)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
        )
    }

    fun unsubscribe() {
        subscription.clear()
    }
}
