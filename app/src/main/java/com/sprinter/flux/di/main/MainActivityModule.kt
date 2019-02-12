package com.sprinter.flux.di.main

import com.sprinter.flux.mvp.presenter.MainPresenter
import com.sprinter.flux.router.Router
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainPresenter(
        router: Router
    ): MainPresenter = MainPresenter(router)
}
