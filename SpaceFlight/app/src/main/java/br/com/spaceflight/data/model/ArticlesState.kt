package br.com.spaceflight.data.model

import android.os.Message

data class ArticlesState(
    val articles: List<Articles> = emptyList(),
    val isLoading: Boolean = false,
    val message: Throwable? = null
)