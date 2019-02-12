package com.sprinter.flux.extension

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

fun AppCompatActivity.hideSoftKeyboard() {
    val inputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (this.currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(
            this.currentFocus!!
                .windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}
