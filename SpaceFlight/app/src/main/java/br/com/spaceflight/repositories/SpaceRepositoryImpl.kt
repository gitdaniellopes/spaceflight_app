package br.com.spaceflight.repositories

import android.os.RemoteException
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.data.remote.SpaceService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class SpaceRepositoryImpl @Inject constructor(
    private val service: SpaceService
) : SpaceRepository {

    override suspend fun getArticles(): Flow<List<Articles>> = flow {
        try {
            val articles = service.getArticles()
            emit(articles)
        } catch (ex: HttpException) {
            throw RemoteException("Não foi possivel acessar a API")
        }
    }

    override suspend fun getArticle(id: Int): Articles {
        TODO("Not yet implemented")
    }
}