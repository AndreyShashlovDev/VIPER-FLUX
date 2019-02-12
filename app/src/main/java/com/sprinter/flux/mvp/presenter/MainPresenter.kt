package com.sprinter.flux.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.sprinter.flux.mvp.contract.MainContract
import com.sprinter.flux.router.Router

@InjectViewState
class MainPresenter(
    private val router: Router
) : AbstractPresenter<MainContract.MainView>(), MainContract.MainPresenter {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        openReposPage()
    }

    override fun openReposPage() {
        router.openReposPage()
    }
}
