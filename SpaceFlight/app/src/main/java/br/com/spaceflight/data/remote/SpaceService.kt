package br.com.spaceflight.data.remote

import br.com.spaceflight.data.model.Articles
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceService {

    companion object {
        const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"
    }

    @GET("articles")
    suspend fun getArticles(): List<Articles>

    @GET("articles/{id}")
    suspend fun getArticleById(
        @Path("id")
        id: Int
    ): Articles
}