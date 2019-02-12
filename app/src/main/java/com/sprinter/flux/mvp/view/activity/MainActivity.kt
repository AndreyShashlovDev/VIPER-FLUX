package com.sprinter.flux.mvp.view.activity

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.sprinter.flux.R
import com.sprinter.flux.mvp.contract.MainContract
import com.sprinter.flux.mvp.presenter.MainPresenter
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
