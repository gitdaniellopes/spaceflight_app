package br.com.spaceflight.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Articles(
    val id: Int? = null,
    val title: String?,
    val url: String?,
    val imageUrl: String?,
    val newsSite: String?,
    val summary: String?,
    val publishedAt: String?,
    val updatedAt: String?,
    val featured: Boolean?,
    val launches: List<Launches>? = null,
    val events: List<Events>? = null,
)