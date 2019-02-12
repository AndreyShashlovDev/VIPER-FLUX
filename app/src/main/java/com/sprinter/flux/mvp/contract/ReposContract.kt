package com.sprinter.flux.mvp.contract

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView
import com.sprinter.flux.mvp.model.Repo

interface ReposContract {

    interface ReposView : MvpView {
        fun setupRepos(repos: List<Repo>)
        fun visibilityLoading(visible: Boolean)
        fun visibilityEmptyListText(visible: Boolean)
        fun toastMessage(@StringRes resId: Int)
    }

    interface ReposPresenter {
        fun onRepoClick(repo: Repo)
    }
}
