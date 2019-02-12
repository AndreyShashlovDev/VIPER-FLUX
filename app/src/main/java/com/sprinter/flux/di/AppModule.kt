package com.sprinter.flux.di

import android.app.Application
import android.content.Context
import com.sprinter.flux.router.Router
import com.sprinter.flux.router.RouterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesAppContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun providesRouter(app: Application): Router = RouterImpl(app)
}
