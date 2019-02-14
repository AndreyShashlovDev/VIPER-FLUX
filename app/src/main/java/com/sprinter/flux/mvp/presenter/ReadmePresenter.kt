package com.sprinter.flux.mvp.presenter

import android.annotation.SuppressLint
import android.util.Base64
import com.arellomobile.mvp.InjectViewState
import com.sprinter.flux.R
import com.sprinter.flux.flux.main.GlobalState
import com.sprinter.flux.flux.main.action.MainActionsCreator
import com.sprinter.flux.flux.main.action.ReadmeActionData.FetchReadmeErrorAction
import com.sprinter.flux.flux.main.action.ReadmeActionData.FetchingReadmeAction
import com.sprinter.flux.flux.main.action.ReadmeActionData.ReceiveReadmeAction
import com.sprinter.flux.mvp.contract.ReadmeContract
import com.sprinter.flux.mvp.model.Readme
import com.sprinter.flux.router.Router
import com.sprinter.fluxlib.Action
import com.sprinter.fluxlib.BaseData
import com.sprinter.fluxlib.ConfigurableStore
import com.sprinter.fluxlib.ReceiveAction
import java.nio.charset.Charset
import javax.inject.Inject

@InjectViewState
class ReadmePresenter @Inject constructor(
    private val router: Router,
    private val store: ConfigurableStore<GlobalState>,
    private val mainActionsCreator: MainActionsCreator
) : AbstractPresenter<ReadmeContract.ReadmeView>(), ReadmeContract.ReadmePresenter {

    @SuppressLint("CheckResult")
    override fun attachView(view: ReadmeContract.ReadmeView) {
        super.attachView(view)

        store.subscribeToAction()
            .compose(bindUntilDetach())
            .subscribe(this::onReceiveAction)

        updateFetchingState(true, false, store.getState().readme)
    }

    override fun setupReadmeData(userName: String, repoName: String) {
        if (store.getState().readme.isEmpty()) {
            store.dispatch(mainActionsCreator.fetchReadme(userName, repoName))
        }
    }

    private fun onReceiveAction(
        receivedAction: ReceiveAction<Action<BaseData>, GlobalState>
    ) {
        val action = receivedAction.action
        val readme = receivedAction.state.readme
        when (action.data) {
            is FetchingReadmeAction -> updateFetchingState(true, false, readme)
            is ReceiveReadmeAction -> updateFetchingState(false, false, readme)
            is FetchReadmeErrorAction -> updateFetchingState(false, true, readme)
        }
    }

    private fun updateFetchingState(fetching: Boolean, hasError: Boolean, readme: Readme) {
        viewState.visibilityLoading(fetching)
        viewState.visibilityEmptyReadmeText(!fetching && readme.content.isBlank())
        if (hasError) {
            viewState.toastMessage(R.string.error_fetch_readme)
        }
        if (readme.content.isNotBlank()) {
            val text = String(
                Base64.decode(readme.content, Base64.DEFAULT), Charset.defaultCharset()
            )
            viewState.setReadme(text)
        }
    }
}
