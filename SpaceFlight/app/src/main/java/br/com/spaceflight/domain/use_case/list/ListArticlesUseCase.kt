package br.com.spaceflight.domain.use_case.list

import br.com.spaceflight.core.util.use_case.UseCase
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.domain.repository.SpaceRepository
import br.com.spaceflight.core.util.state.NetworkState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListArticlesUseCase @Inject constructor(
    private val repository: SpaceRepository
) : UseCase.NoParam<NetworkState<List<Articles>>>() {

    //override suspend fun execute(): Flow<List<Articles>> = repository.getArticles()
//    suspend fun getAll(): Flow<NetworkState<List<Articles>>> =
    override suspend fun execute(): Flow<NetworkState<List<Articles>>> = repository.getArticles()
}