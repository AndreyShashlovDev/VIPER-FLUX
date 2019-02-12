package com.sprinter.flux.mvp.contract

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ReadmeContract {

    interface ReadmeView : MvpView {
        fun visibilityLoading(visible: Boolean)
        fun visibilityEmptyReadmeText(visible: Boolean)
        @StateStrategyType(SkipStrategy::class)
        fun toastMessage(@StringRes resId: Int)
        fun setReadme(readme: String)
    }

    interface ReadmePresenter {
        fun setupReadmeData(userName: String, repoName: String)
    }
}
