package br.com.spaceflight.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Launches(
    val id: String,
    val provider: String
) : Parcelable