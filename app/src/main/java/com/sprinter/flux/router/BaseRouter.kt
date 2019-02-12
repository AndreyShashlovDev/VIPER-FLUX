package com.sprinter.flux.router

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.AnimatorRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.sprinter.flux.AppDelegate
import com.sprinter.flux.R
import com.sprinter.flux.extension.hideSoftKeyboard
import com.sprinter.flux.mvp.view.activity.BaseActivityView
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class BaseRouter(private val application: Application) :
    Application.ActivityLifecycleCallbacks,
    BaseActivityView.OnBackPressedListener {

    protected var activityOnTop: WeakReference<AppCompatActivity>? = null

    init {
        val appDelegate = application as AppDelegate
        appDelegate.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityDestroyed(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        setUpTopActivity(activity as AppCompatActivity)
    }

    override fun onActivityResumed(activity: Activity?) {
        setUpTopActivity(activity as AppCompatActivity)
    }

    override fun onActivityStopped(activity: Activity?) {
        if (activity is BaseActivityView) {
            (activity as BaseActivityView).setOnBackPressedListener(null)
        }
    }

    private fun setUpTopActivity(activity: AppCompatActivity?) {
        if (activity is BaseActivityView) {
            (activity as BaseActivityView).setOnBackPressedListener(this)
        }
        activityOnTop = WeakReference(activity!!)
    }

    override fun onBackPressed(): Boolean {
        val activity = activityOnTop?.get()
        val manager = activity?.supportFragmentManager
        val backStackEntryCount = manager?.backStackEntryCount ?: 0

        val view = activity?.currentFocus
        view?.clearFocus()

        if (backStackEntryCount < 2) {
            activity?.supportFinishAfterTransition()
            return false
        }

        return true
    }

    protected fun changeFragment(
        fragment: Fragment,
        tag: String,
        clearBackStack: Boolean,
        replace: Boolean = true,
        @AnimatorRes @AnimRes enter: Int = 0,
        @AnimatorRes @AnimRes exit: Int = 0,
        @AnimatorRes @AnimRes popEnter: Int = 0,
        @AnimatorRes @AnimRes popExit: Int = 0
    ) {
        try {
            val activity = activityOnTop?.get()
            val manager = activity?.supportFragmentManager ?: return

            if (clearBackStack) {
                try {
                    manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                } catch (e: IllegalStateException) {
                    Timber.e(e.message)
                }
            }

            val transaction = manager.beginTransaction().addToBackStack(tag)

            transaction.setCustomAnimations(enter, exit, popEnter, popExit)

            if (replace) {
                transaction.replace(R.id.container, fragment, tag)
            } else {
                transaction.add(R.id.container, fragment, tag)
            }
            transaction.commitAllowingStateLoss()
            activity.hideSoftKeyboard()
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.d(e, "try changeFragment")
        }
    }
}
