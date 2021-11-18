package br.com.spaceflight.data.repository

import android.app.Application
import android.os.RemoteException
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.data.remote.SpaceService
import br.com.spaceflight.core.util.state.NetworkState
import br.com.spaceflight.domain.repository.SpaceRepository
import br.com.spaceflight.core.util.checkForInternetConnection
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
                emit(NetworkState.ErrorString("Sem conexão com a internet"))
            }
        } catch (ex: Exception) {
            emit(NetworkState.ErrorString(ex.toString()))
        } catch (ex: HttpException) {
            emit(NetworkState.ErrorString("Um erro ocorreu"))
        } catch (ex: IOException) {
            emit(NetworkState.ErrorString("Erro no servidor, verifique a conexão com a internet"))
        }
    }.flowOn(Dispatchers.IO)
}