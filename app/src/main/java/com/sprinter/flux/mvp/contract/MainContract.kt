package com.sprinter.flux.mvp.contract

import com.arellomobile.mvp.MvpView

interface MainContract {

    interface MainView : MvpView

    interface MainPresenter {
        fun openReposPage()
    }
}
