package com.sprinter.flux

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.sprinter.flux.di.DaggerAppComponent
import com.sprinter.flux.router.Router
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class AppDelegate : Application(), HasSupportFragmentInjector, HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingAndroidFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var router: Router

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? = dispatchingAndroidActivityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
        dispatchingAndroidFragmentInjector
}
