package com.sprinter.flux.mvp.model

import com.sprinter.flux.repository.entity.ReadmeEntity

data class Readme(
    val name: String,
    val content: String,
    val url: String,
    val git_url: String,
    val html_url: String,
    val isBroken: Boolean = false
) {
    companion object {
        val empty: Readme = Readme("", "", "", "", "")
        val brokenReadme: Readme = Readme("", "", "", "", "", true)

        fun valueOf(readmeEntity: ReadmeEntity): Readme = Readme(
            readmeEntity.name,
            readmeEntity.content,
            readmeEntity.url,
            readmeEntity.git_url,
            readmeEntity.html_url
        )
    }

    fun isEmpty(): Boolean {
        return name.isBlank() && !isBroken
    }
}