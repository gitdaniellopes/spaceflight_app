package br.com.spaceflight.repositories

import br.com.spaceflight.data.model.Articles
import kotlinx.coroutines.flow.Flow

interface SpaceRepository {

    suspend fun getArticles(): Flow<List<Articles>>
    suspend fun getArticle(id: Int): Flow<Articles>
}