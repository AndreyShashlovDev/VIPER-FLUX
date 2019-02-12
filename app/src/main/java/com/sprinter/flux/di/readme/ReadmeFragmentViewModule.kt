package com.sprinter.flux.di.readme

import com.sprinter.flux.mvp.contract.ReadmeContract
import com.sprinter.flux.mvp.view.fragment.ReadmeFragment
import dagger.Binds
import dagger.Module

@Module
abstract class ReadmeFragmentViewModule {

    @Binds
    internal abstract fun provideReadmeView(
        readmeFragment: ReadmeFragment
    ): ReadmeContract.ReadmeView
}
