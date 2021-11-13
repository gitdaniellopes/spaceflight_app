package br.com.spaceflight.domain

import br.com.spaceflight.core.UseCase
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.repositories.SpaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListArticlesUseCase @Inject constructor(
    private val repository: SpaceRepository
) : UseCase.NoParam<List<Articles>>() {

    override suspend fun execute(): Flow<List<Articles>> = repository.getArticles()
}