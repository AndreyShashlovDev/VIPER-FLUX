package com.sprinter.flux.flux.main

import android.annotation.SuppressLint
import com.sprinter.flux.interactor.ReadmeInteractor
import com.sprinter.flux.interactor.ReadmeParams
import com.sprinter.flux.mvp.model.Readme
import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.MutableStore
import com.sprinter.fluxlib.Reducer
import io.reactivex.Observable

class ReadmeReducer(
    private val readmeInteractor: ReadmeInteractor,
    private val mainActionsCreator: MainActionsCreator
) : Reducer<GlobalState, MainActionData>() {

    override fun execute(
        store: MutableStore<GlobalState>,
        state: GlobalState,
        action: Action<MainActionData>
    ): Observable<*> {
        return when (action.data) {
            is MainActionData.FetchReadmeAction ->
                fetchReadme(
                    store, state,
                    action.data as MainActionData.FetchReadmeAction
                )
            else -> Observable.empty<Void>()
        }
    }

    override fun isServiceAction(action: Action<MainActionData>): Boolean {
        return action.data is MainActionData.FetchReadmeAction
    }

    @SuppressLint("CheckResult")
    private fun fetchReadme(
        store: MutableStore<GlobalState>,
        state: GlobalState,
        data: MainActionData.FetchReadmeAction
    ): Observable<*> {
        store.dispatch(mainActionsCreator.fetchingReadme())
        val observable = readmeInteractor
            .buildObservable(ReadmeParams(data.owner, data.repoName))

        observable.subscribe({ result ->
            val updatedState = state.copy(
                readme = result
            )
            store.updateState(updatedState)
            store.dispatch(mainActionsCreator.receiveReadme())
        }, { error ->
            val updatedState = state.copy(
                readme = Readme.brokenReadme
            )
            store.updateState(updatedState)
            store.dispatch(mainActionsCreator.fetchReadmeError(error))
        })

        return observable
    }
}
