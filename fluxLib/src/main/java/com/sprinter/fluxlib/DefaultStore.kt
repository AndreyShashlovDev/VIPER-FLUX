package com.sprinter.fluxlib

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

open class DefaultStore<S : State>(private val state: S) : ConfigurableStore<S> {

    companion object {
        private val SCHEDULER_SINGLE_THREAD = Schedulers.from(
            Executors.newSingleThreadExecutor()
        )
    }

    private val reducers = HashSet<Reducer<S, BaseData>>()
    private val middlewares = HashSet<Middleware<S>>()
    private val flowableProcessor = PublishProcessor.create<Action<BaseData>>()
    private val storeAction = LinkedBlockingQueue<Action<BaseData>>()
    private val subject = BehaviorSubject.create<S>()
    private val subjectAction = PublishProcessor.create<ReceiveAction<Action<BaseData>, S>>()

    init {
        subject.onNext(state)

        flowableProcessor
            .observeOn(SCHEDULER_SINGLE_THREAD)
            .subscribe { execute(it) }
    }

    override fun registerReducer(reducer: Reducer<S, out BaseData>): ConfigurableStore<S> {
        @Suppress("UNCHECKED_CAST")
        reducers.add(reducer as Reducer<S, BaseData>)
        return this
    }

    override fun registerMiddleware(middleware: Middleware<S>): DefaultStore<S> {
        middlewares.add(middleware)
        return this
    }

    override fun dispatch(action: Action<BaseData>) {
        storeAction.offer(action)
        flowableProcessor.offer(action)
        subjectAction.onNext(ReceiveAction(action, subject.value!!))
    }

    override fun getState(): S = subject.value!!

    override fun subscribeToAction(): Observable<ReceiveAction<Action<BaseData>, S>> =
        subjectAction
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()

    @SuppressLint("CheckResult")
    private fun execute(action: Action<BaseData>): DefaultStore<S> {
        val filteredReducer = reducers
            .filter { reducer -> reducer.isServiceAction(action) }

        val defaultAction = ReceiveAction(action, subject.value!!)
        var start: Observable<ReceiveAction<Action<BaseData>, S>> =
            Observable.fromCallable { return@fromCallable defaultAction }

        filteredReducer.forEach { filtered ->
            start = start.flatMap {
                filtered.execute(this, subject.value!!, action)
            }
        }

        middlewares.forEach { middleware -> start = middleware.interceptor(action, state, start) }
        start
            .subscribe {
                subject.onNext(it.state)
                if (!storeAction.contains(it.action)) {
                    dispatch(it.action)
                }
            }

        return this
    }
}
