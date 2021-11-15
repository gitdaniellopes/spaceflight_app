package br.com.spaceflight.repositories

import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.util.State
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SpaceRepository {

    suspend fun getArticles(): Flow<List<Articles>>
    suspend fun getArticle(id: Int): Flow<Articles>
}