package com.sprinter.fluxlib

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

open class DefaultStore<S : State>(private val state: S) : MutableStore<S> {

    companion object {
        private val SCHEDULER_SINGLE_THREAD = Schedulers.from(
            Executors.newSingleThreadExecutor()
        )
    }

    data class ReceiveAction<T : Action<BaseData>, S>(
        val action: T,
        val state: S
    )

    private val reducers = HashSet<Reducer<S, BaseData>>()
    private val middlewares = HashSet<Middleware<S>>()
    private val flowableProcessor = PublishProcessor.create<Action<BaseData>>()
    private val storeAction = LinkedBlockingQueue<Action<BaseData>>()
    private val subject = BehaviorSubject.create<S>()
    private val subjectAction = BehaviorSubject.create<ReceiveAction<Action<BaseData>, S>>()

    init {
        subject.onNext(state)

        flowableProcessor
            .observeOn(SCHEDULER_SINGLE_THREAD)
            .subscribe { execute(it) }
    }

    override fun registerReducer(reducer: Reducer<S, out BaseData>): MutableStore<S> {
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
    }

    override fun getState(): S = subject.value!!

    override fun subscribeToAction(): Observable<ReceiveAction<Action<BaseData>, S>> =
        subjectAction
            .observeOn(AndroidSchedulers.mainThread())

    override fun updateState(updatedState: S) {
        subject.onNext(updatedState)
    }

    private fun execute(action: Action<BaseData>): DefaultStore<S> {
        val filteredReducer = reducers
            .filter { reducer -> reducer.isServiceAction(action) }

        filteredReducer.forEach { filtered ->
            filtered.execute(this, subject.value!!, action)
        }

        middlewares.forEach { middleware -> middleware.interceptor(action, state) }

        subjectAction.onNext(
            ReceiveAction(
                action,
                subject.value!!
            )
        )
        return this
    }
}
