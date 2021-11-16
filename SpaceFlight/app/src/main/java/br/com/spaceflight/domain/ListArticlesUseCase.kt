package br.com.spaceflight.domain

import br.com.spaceflight.core.UseCase
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.repositories.SpaceRepository
import br.com.spaceflight.util.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListArticlesUseCase @Inject constructor(
    private val repository: SpaceRepository
) : UseCase.NoParam<State<List<Articles>>>() {

    //override suspend fun execute(): Flow<List<Articles>> = repository.getArticles()
//    suspend fun getAll(): Flow<State<List<Articles>>> =
    override suspend fun execute(): Flow<State<List<Articles>>> = repository.getArticles2()
}