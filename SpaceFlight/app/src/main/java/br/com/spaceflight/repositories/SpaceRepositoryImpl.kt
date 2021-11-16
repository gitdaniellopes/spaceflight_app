package br.com.spaceflight.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.RemoteException
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.data.remote.SpaceService
import br.com.spaceflight.util.State
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


    override suspend fun getArticles(): Flow<List<Articles>> = flow {
        try {
            val articles = service.getArticles()
            emit(articles)
        } catch (ex: HttpException) {
            throw RemoteException("Não foi possivel acessar a API")
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun getArticle(id: Int): Flow<Articles> = flow {
        try {
            val article = service.getArticleById(id)
            emit(article)
        } catch (ex: HttpException) {
            throw RemoteException("Não foi possivel acessar a API")
        }
    }.flowOn(Dispatchers.IO)

    //seguir esse
    override suspend fun getArticles2(): Flow<State<List<Articles>>> = flow {
        emit(State.Loading)
        try {
            if (checkForInternetConnection(context)) {
                val response = service.getArticles2()
                if (response.isNullOrEmpty()) {
                    emit(State.Empty)
                } else {
                    emit(State.Success(response))
                }
            } else {
                emit(State.Error2("Sem conexão com a internet"))
            }
        } catch (ex: Exception) {
            emit(State.Error2(ex.toString()))
        } catch (ex: HttpException) {
            emit(State.Error2("Um erro ocorreu"))
        } catch (ex: IOException) {
            emit(State.Error2("Erro no servidor, verifique a conexão com a internet"))
        }
    }.flowOn(Dispatchers.IO)
}

fun checkForInternetConnection(context: Application): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
        }
    }
    return connectivityManager.activeNetworkInfo?.isAvailable ?: false
}