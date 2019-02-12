package com.sprinter.flux.di

import android.app.Application
import com.sprinter.flux.AppDelegate
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FluxModule::class,
        AppModule::class,
        RepositoriesModule::class,
        BuilderModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppDelegate)
}
