package br.com.spaceflight.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Articles(
    val id: Int? = null,
    val title: String?,
    val url: String?,
    val imageUrl: String?,
    val newsSite: String?,
    val summary: String?,
    val publishedAt: String,
    val updatedAt: String?,
    val featured: Boolean?,
    val launches: List<Launches> = emptyList(),
    val events: List<Events> = emptyList(),
) : Parcelable {

    fun hasLaunches(): Boolean = launches.isNotEmpty()
    fun getLaunchesCount(): Int = launches.size
}