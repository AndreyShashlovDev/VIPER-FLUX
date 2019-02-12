package com.sprinter.flux.holders

import android.view.View
import com.sprinter.flux.mvp.model.Repo
import com.sprinter.viper.holders.AbstractHolder
import kotlinx.android.synthetic.main.li_repos.view.*

class ReposHolder constructor(itemView: View) : AbstractHolder<Repo>(itemView) {

    override fun bind(model: Repo) {
        super.bind(model)
        itemView.liReposTitle.text = model.repoName
    }
}
