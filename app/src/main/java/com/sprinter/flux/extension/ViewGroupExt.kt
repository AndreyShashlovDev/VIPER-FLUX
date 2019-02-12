package com.sprinter.viper.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes resId: Int): View {
    val inflater = LayoutInflater.from(this.context)
    return inflater.inflate(resId, this, false)
}
