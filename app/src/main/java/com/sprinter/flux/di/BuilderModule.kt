package com.sprinter.flux.di

import com.sprinter.flux.di.main.MainActivityModule
import com.sprinter.flux.di.main.MainActivityViewModule
import com.sprinter.flux.di.readme.ReadmeFragmentModule
import com.sprinter.flux.di.readme.ReadmeFragmentViewModule
import com.sprinter.flux.di.repos.ReposFragmentModule
import com.sprinter.flux.di.repos.ReposFragmentViewModule
import com.sprinter.flux.mvp.view.activity.MainActivity
import com.sprinter.flux.mvp.view.fragment.ReadmeFragment
import com.sprinter.flux.mvp.view.fragment.ReposFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [MainActivityViewModule::class, MainActivityModule::class]
    )
    abstract fun bindMainActivityModule(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [ReposFragmentViewModule::class, ReposFragmentModule::class]
    )
    abstract fun bindReposFragmentModule(): ReposFragment

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [ReadmeFragmentViewModule::class, ReadmeFragmentModule::class]
    )
    abstract fun bindReadmeFragmentModule(): ReadmeFragment
}
