package com.sprinter.flux.mvp.view.activity

interface BaseActivityView {

    interface OnBackPressedListener {

        fun onBackPressed(): Boolean
    }

    fun setOnBackPressedListener(listener: OnBackPressedListener?)
}
