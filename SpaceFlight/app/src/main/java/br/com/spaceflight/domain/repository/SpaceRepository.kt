package br.com.spaceflight.domain.repository

import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.core.util.state.NetworkState
import kotlinx.coroutines.flow.Flow

interface SpaceRepository {
    suspend fun getArticle(id: Int): Flow<Articles>
    suspend fun getArticles(): Flow<NetworkState<List<Articles>>>
}