package com.sprinter.flux.di.repos

import com.sprinter.flux.mvp.contract.ReposContract
import com.sprinter.flux.mvp.view.fragment.ReposFragment
import dagger.Binds
import dagger.Module

@Module
abstract class ReposFragmentViewModule {

    @Binds
    internal abstract fun provideReposView(reposFragment: ReposFragment): ReposContract.ReposView
}
