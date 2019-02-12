package com.sprinter.flux.flux.main

import com.sprinter.flux.mvp.model.Readme
import com.sprinter.flux.mvp.model.Repo
import com.sprinter.fluxlib.State

data class ReposState(
    val items: List<Repo>,
    val selectedItem: Int
)

data class GlobalState(
    val ver: Int = 0,
    val reposState: ReposState = ReposState(ArrayList(), -1),
    val readme: Readme = Readme.empty
) : State()
