package com.sprinter.flux.mvp.view.activity

import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpView
import com.sprinter.flux.extension.hideSoftKeyboard

abstract class BaseActivity : MvpAppCompatActivity(), BaseActivityView, MvpView {

    private var backPressedListener: BaseActivityView.OnBackPressedListener? = null

    override fun setOnBackPressedListener(listener: BaseActivityView.OnBackPressedListener?) {
        backPressedListener = listener
    }

    override fun onBackPressed() {
        this.hideSoftKeyboard()
        if (backPressedListener?.onBackPressed() == true) {
            super.onBackPressed()
        }
    }
}
