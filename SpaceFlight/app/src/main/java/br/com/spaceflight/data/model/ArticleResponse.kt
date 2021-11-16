package br.com.spaceflight.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    @SerialName("articles")
    var articles: MutableList<Articles>
)
