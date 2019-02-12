package com.sprinter.flux.adapters

import android.view.ViewGroup
import com.sprinter.flux.R
import com.sprinter.flux.holders.ReposHolder
import com.sprinter.flux.mvp.model.Repo
import com.sprinter.viper.adapters.AbstractRecyclerAdapter
import com.sprinter.viper.extensions.inflate

class ReposRecyclerAdapter : AbstractRecyclerAdapter<Repo, ReposHolder>(emptyList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposHolder =
        ReposHolder(parent.inflate(R.layout.li_repos))
}
