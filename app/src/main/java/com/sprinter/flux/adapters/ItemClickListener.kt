package com.sprinter.viper.adapters

import android.support.annotation.IdRes

interface ItemClickListener {

    fun onItemClick(position: Int, @IdRes id: Int, payload: String?)
}
