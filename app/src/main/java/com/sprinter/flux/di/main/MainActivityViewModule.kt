package com.sprinter.flux.di.main

import com.sprinter.flux.mvp.contract.MainContract
import com.sprinter.flux.mvp.view.activity.MainActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityViewModule {

    @Binds
    internal abstract fun provideMainView(mainActivity: MainActivity): MainContract.MainView
}
