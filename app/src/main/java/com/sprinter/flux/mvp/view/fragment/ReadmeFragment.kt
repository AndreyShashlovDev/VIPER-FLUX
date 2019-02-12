package com.sprinter.flux.mvp.view.fragment

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.sprinter.flux.R
import com.sprinter.flux.extension.toast
import com.sprinter.flux.extension.visible
import com.sprinter.flux.mvp.contract.ReadmeContract
import com.sprinter.flux.mvp.presenter.ReadmePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fmt_readme.*
import javax.inject.Inject

class ReadmeFragment : MvpAppCompatFragment(), ReadmeContract.ReadmeView {

    companion object {
        val FRAGMENT_TAG: String
            get() = ReadmeFragment::class.java.simpleName

        private const val KEY_USER_NAME: String = "KEY_USER_NAME"
        private const val KEY_REPO_NAME: String = "KEY_REPO_NAME"

        @JvmStatic
        fun newInstance(userName: String, repoName: String): Fragment {
            val args = Bundle()
            args.putString(KEY_USER_NAME, userName)
            args.putString(KEY_REPO_NAME, repoName)
            val fragment = ReadmeFragment()
            fragment.arguments = args

            return fragment
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: ReadmePresenter

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
        return inflater.inflate(R.layout.fmt_readme, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName: String = arguments?.get(KEY_USER_NAME) as String
        val repoName: String = arguments?.get(KEY_REPO_NAME) as String
        presenter.setupReadmeData(userName, repoName)
    }

    override fun visibilityLoading(visible: Boolean) {
        if (visible) fmtReadmeLoading.show() else fmtReadmeLoading.hide()
    }

    override fun visibilityEmptyReadmeText(visible: Boolean) {
        fmtReadmeEmpty.visible(visible)
    }

    override fun setReadme(readme: String) {
        fmtReadmeText.setText(readme)
    }

    override fun toastMessage(@StringRes resId: Int) {
        context!!.toast(resId)
    }
}
