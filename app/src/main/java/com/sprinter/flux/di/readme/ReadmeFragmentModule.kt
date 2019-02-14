package com.sprinter.flux.di.readme

import com.sprinter.flux.flux.main.GlobalState
import com.sprinter.flux.flux.main.MainActionsCreator
import com.sprinter.flux.interactor.ReadmeInteractor
import com.sprinter.flux.mvp.contract.ReadmeContract
import com.sprinter.flux.mvp.presenter.ReadmePresenter
import com.sprinter.flux.repository.github.GithubRepository
import com.sprinter.flux.router.Router
import com.sprinter.fluxlib.ConfigurableStore
import dagger.Module
import dagger.Provides

@Module
class ReadmeFragmentModule {

    @Provides
    fun provideReadmePresenter(
        router: Router,
        store: ConfigurableStore<GlobalState>,
        mainActionsCreator: MainActionsCreator
    ): ReadmeContract.ReadmePresenter =
        ReadmePresenter(router, store, mainActionsCreator)

    @Provides
    fun provideReadmeInteractor(githubRepository: GithubRepository): ReadmeInteractor =
        ReadmeInteractor(githubRepository)
}
