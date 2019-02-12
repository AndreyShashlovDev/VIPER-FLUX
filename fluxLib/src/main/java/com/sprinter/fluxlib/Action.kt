package com.sprinter.fluxlib

data class Action<out T>(
    val data: T
)
