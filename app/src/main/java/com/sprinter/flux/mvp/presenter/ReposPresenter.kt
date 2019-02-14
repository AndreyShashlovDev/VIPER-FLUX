package com.sprinter.flux.mvp.presenter

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.sprinter.flux.R
import com.sprinter.flux.flux.main.GlobalState
import com.sprinter.flux.flux.main.ReposActionData.FetchReposErrorAction
import com.sprinter.flux.flux.main.ReposActionData.FetchingReposAction
import com.sprinter.flux.flux.main.ReposActionData.ReceiveReposAction
import com.sprinter.flux.flux.main.MainActionsCreator
import com.sprinter.flux.mvp.contract.ReposContract
import com.sprinter.flux.mvp.model.Repo
import com.sprinter.flux.router.Router
import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.ConfigurableStore
import com.sprinter.fluxlib.ReceiveAction
import javax.inject.Inject

@InjectViewState
class ReposPresenter @Inject constructor(
    private val router: Router,
    private val store: ConfigurableStore<GlobalState>,
    private val mainActionsCreator: MainActionsCreator
) : AbstractPresenter<ReposContract.ReposView>(), ReposContract.ReposPresenter {

    @SuppressLint("CheckResult")
    override fun attachView(view: ReposContract.ReposView) {
        super.attachView(view)

        store.subscribeToAction()
            .compose(bindUntilDetach())
            .subscribe(this::onReceiveAction)

        if (store.getState().reposState.items.isEmpty()) {
            store.dispatch(mainActionsCreator.fetchRepos("SprinterAstra"))
        }
    }

    override fun onRepoClick(repo: Repo) {
        router.openReadmePage(repo.userName, repo.repoName)
    }

    private fun onReceiveAction(
        receivedAction: ReceiveAction<Action<BaseData>, GlobalState>
    ) {
        val action = receivedAction.action
        val items = receivedAction.state.reposState.items

        when (action.data) {
            is FetchingReposAction -> updateFetchingState(true, false, items)
            is ReceiveReposAction -> updateFetchingState(false, false, items)
            is FetchReposErrorAction -> updateFetchingState(false, true, items)
        }
    }

    private fun updateFetchingState(fetching: Boolean, hasError: Boolean, list: List<Repo>) {
        viewState.visibilityLoading(fetching)
        viewState.visibilityEmptyListText(!fetching && list.isEmpty())
        viewState.setupRepos(list)
        if (hasError) {
            viewState.toastMessage(R.string.error_fetch_repos)
        }
    }
}
