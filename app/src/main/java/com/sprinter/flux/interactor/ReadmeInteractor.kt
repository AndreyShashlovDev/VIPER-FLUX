package com.sprinter.flux.interactor

import com.sprinter.flux.mvp.model.Readme
import com.sprinter.flux.repository.github.GithubRepository
import io.reactivex.Observable
import javax.inject.Inject

data class ReadmeParams(
    val userName: String,
    val repo: String
)

class ReadmeInteractor @Inject constructor(
    private val repository: GithubRepository
) :
    AbstractInteractor<Readme, ReadmeParams>() {

    override fun buildObservable(parameter: ReadmeParams): Observable<Readme> =
        repository.getReadme(parameter.userName, parameter.repo)
            .map { entity -> Readme.valueOf(entity) }
            .toObservable()
}
