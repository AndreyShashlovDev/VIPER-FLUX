package com.sprinter.flux.mvp.contract

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView

interface ReadmeContract {

    interface ReadmeView : MvpView {
        fun visibilityLoading(visible: Boolean)
        fun visibilityEmptyReadmeText(visible: Boolean)
        fun toastMessage(@StringRes resId: Int)
        fun setReadme(readme: String)
    }

    interface ReadmePresenter {
        fun setupReadmeData(userName: String, repoName: String)
    }
}
