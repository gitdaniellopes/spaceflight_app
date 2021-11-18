package br.com.spaceflight.domain.use_case.details

import br.com.spaceflight.core.util.use_case.UseCase
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.domain.repository.SpaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsArticleUseCase @Inject constructor(
    private val repository: SpaceRepository
): UseCase<Int, Articles>() {
    override suspend fun execute(param: Int): Flow<Articles> = repository.getArticle(param)
}