package com.sprinter.flux.interactor

import com.sprinter.flux.mvp.model.Repo
import com.sprinter.flux.repository.github.GithubRepository
import io.reactivex.Observable
import javax.inject.Inject

class ReposInteractor @Inject constructor(
    private val repository: GithubRepository
) :
    AbstractInteractor<List<Repo>, String>() {

    override fun buildObservable(userName: String): Observable<List<Repo>> =
        repository.getUserRepos(userName)
            .map { entities ->
                entities.map { entity ->
                    Repo(entity.id, entity.name, userName)
                }
            }
            .toObservable()
}
