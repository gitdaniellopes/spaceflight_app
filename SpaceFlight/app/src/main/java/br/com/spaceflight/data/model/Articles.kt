package br.com.spaceflight.data.model

data class Articles(
    val id: String? = "",
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