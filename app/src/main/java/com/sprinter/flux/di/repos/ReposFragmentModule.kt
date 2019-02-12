package com.sprinter.flux.di.repos

import com.sprinter.fluxlib.MutableStore
import com.sprinter.flux.flux.main.GlobalState
import com.sprinter.flux.flux.main.MainActionsCreator
import com.sprinter.flux.interactor.ReposInteractor
import com.sprinter.flux.mvp.contract.ReposContract
import com.sprinter.flux.mvp.presenter.ReposPresenter
import com.sprinter.flux.repository.github.GithubRepository
import com.sprinter.flux.router.Router
import dagger.Module
import dagger.Provides

@Module
class ReposFragmentModule {

    @Provides
    fun provideReposPresenter(
        router: Router,
        store: MutableStore<GlobalState>,
        mainActionsCreator: MainActionsCreator
    ): ReposContract.ReposPresenter =
        ReposPresenter(router, store, mainActionsCreator)

    @Provides
    fun provideReposInteractor(ordersRepository: GithubRepository): ReposInteractor =
        ReposInteractor(ordersRepository)
}
