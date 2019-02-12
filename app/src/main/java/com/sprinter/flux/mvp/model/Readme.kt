package com.sprinter.flux.mvp.model

import com.sprinter.flux.repository.entity.ReadmeEntity

data class Readme(
    val name: String,
    val content: String,
    val url: String,
    val git_url: String,
    val html_url: String
) {
    companion object {
        val empty: Readme = Readme("", "", "", "", "")

        fun valueOf(readmeEntity: ReadmeEntity): Readme = Readme(
            readmeEntity.name,
            readmeEntity.content,
            readmeEntity.url,
            readmeEntity.git_url,
            readmeEntity.html_url
        )
    }
}