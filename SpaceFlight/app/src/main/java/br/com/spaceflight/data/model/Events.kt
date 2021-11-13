package br.com.spaceflight.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Events(
    val id: Int? = null,
    val provider: String
)
