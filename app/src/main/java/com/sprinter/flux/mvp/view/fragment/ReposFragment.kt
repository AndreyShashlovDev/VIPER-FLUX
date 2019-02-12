package com.sprinter.flux.mvp.view.fragment

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.sprinter.flux.R
import com.sprinter.flux.adapters.ReposRecyclerAdapter
import com.sprinter.flux.extension.toast
import com.sprinter.flux.extension.visible
import com.sprinter.flux.mvp.contract.ReposContract
import com.sprinter.flux.mvp.model.Repo
import com.sprinter.flux.mvp.presenter.ReposPresenter
import com.sprinter.viper.adapters.ItemClickListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fmt_repos.*
import javax.inject.Inject

class ReposFragment : MvpAppCompatFragment(), ReposContract.ReposView, ItemClickListener {

    companion object {
        val FRAGMENT_TAG: String
            get() = ReposFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = ReposFragment()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: ReposPresenter

    private val adapter = ReposRecyclerAdapter()

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fmt_repos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fmtReposList.layoutManager = LinearLayoutManager(context)
        fmtReposList.adapter = adapter
        adapter.setClickListener(this)
    }

    override fun onItemClick(position: Int, id: Int, payload: String?) {
        presenter.onRepoClick(adapter.data[position])
    }

    override fun setupRepos(repos: List<Repo>) {
        adapter.data = repos
    }

    override fun visibilityLoading(visible: Boolean) {
        if (visible) fmtReposLoading.show() else fmtReposLoading.hide()
    }

    override fun visibilityEmptyListText(visible: Boolean) {
        fmtReposEmptyList.visible(visible)
    }

    override fun toastMessage(@StringRes resId: Int) {
        context!!.toast(resId)
    }
}
