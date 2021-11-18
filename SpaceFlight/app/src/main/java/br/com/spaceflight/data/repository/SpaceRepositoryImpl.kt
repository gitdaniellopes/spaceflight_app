package br.com.spaceflight.data.repository

import android.app.Application
import android.os.RemoteException
import br.com.spaceflight.core.util.checkForInternetConnection
import br.com.spaceflight.core.util.state.NetworkState
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.data.remote.SpaceService
import br.com.spaceflight.domain.repository.SpaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SpaceRepositoryImpl @Inject constructor(
    private val service: SpaceService,
    private val context: Application
) : SpaceRepository {

    override suspend fun getArticle(id: Int): Flow<Articles> = flow {
        try {
            val article = service.getArticleById(id)
            emit(article)
        } catch (ex: HttpException) {
            throw RemoteException("Não foi possivel acessar a API")
        }
    }.flowOn(Dispatchers.IO)

    //seguir esse
    override suspend fun getArticles(): Flow<NetworkState<List<Articles>>> = flow {
        try {
            emit(NetworkState.Loading)
            if (checkForInternetConnection(context)) {
                val response = service.getArticles()
                if (response.isNullOrEmpty()) {
                    emit(NetworkState.Empty)
                } else {
                    emit(NetworkState.Success(response))
                }
            } else {
                emit(NetworkState.Error("Sem conexão com a internet"))
            }
        } catch (ex: Exception) {
            emit(NetworkState.Error(ex.toString()))
        } catch (ex: HttpException) {
            emit(NetworkState.Error("Um erro ocorreu"))
        } catch (ex: IOException) {
            emit(NetworkState.Error("Erro no servidor, verifique a conexão com a internet"))
        }
    }.flowOn(Dispatchers.IO)
}