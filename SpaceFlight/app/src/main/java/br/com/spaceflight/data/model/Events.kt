package br.com.spaceflight.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Events(
    val id: Int? = null,
    val provider: String
) : Parcelable
