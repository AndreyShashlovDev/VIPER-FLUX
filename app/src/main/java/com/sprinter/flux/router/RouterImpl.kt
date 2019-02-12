package com.sprinter.flux.router

import android.app.Application
import com.sprinter.flux.mvp.view.fragment.ReadmeFragment
import com.sprinter.flux.mvp.view.fragment.ReposFragment

class RouterImpl(app: Application) : BaseRouter(app), Router {

    override fun openReposPage() {
        changeFragment(ReposFragment.newInstance(), ReposFragment.FRAGMENT_TAG, true)
    }

    override fun openReadmePage(userName: String, repoName: String) {
        changeFragment(
            ReadmeFragment.newInstance(userName, repoName),
            ReadmeFragment.FRAGMENT_TAG,
            false
        )
    }
}
