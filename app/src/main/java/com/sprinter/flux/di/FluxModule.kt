package com.sprinter.flux.di

import com.sprinter.flux.flux.main.middleware.CrashReportMiddleware
import com.sprinter.flux.flux.main.GlobalState
import com.sprinter.flux.flux.main.middleware.LoggingMiddleware
import com.sprinter.flux.flux.main.action.MainActionsCreator
import com.sprinter.flux.flux.main.reducer.ReadmeReducer
import com.sprinter.flux.flux.main.reducer.ReposReducer
import com.sprinter.flux.interactor.ReadmeInteractor
import com.sprinter.flux.interactor.ReposInteractor
import com.sprinter.fluxlib.DefaultStore
import com.sprinter.fluxlib.ConfigurableStore
import dagger.Module
import dagger.Provides

@Module
class FluxModule {

    @Provides
    fun provideMainStore(
        reposInteractor: ReposInteractor,
        readmeInteractor: ReadmeInteractor,
        mainActionsCreator: MainActionsCreator
    ): ConfigurableStore<GlobalState> =
        DefaultStore(GlobalState())
            .registerReducer(
                ReadmeReducer(
                    readmeInteractor,
                    mainActionsCreator
                )
            )
            .registerReducer(
                ReposReducer(
                    reposInteractor,
                    mainActionsCreator
                )
            )
//            .registerMiddleware(DelayMiddleware()) // sample of delay 15sec before execute each action
            .registerMiddleware(LoggingMiddleware())
            .registerMiddleware(CrashReportMiddleware())

    @Provides
    fun provideMainActionsCreator() = MainActionsCreator()
}
