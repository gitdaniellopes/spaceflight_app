package br.com.spaceflight.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Launches(
    val id: String,
    val provider: String
)