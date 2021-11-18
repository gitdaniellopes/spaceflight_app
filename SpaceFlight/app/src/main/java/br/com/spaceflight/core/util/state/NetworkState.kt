package br.com.spaceflight.core.util.state

sealed class NetworkState<out T : Any> {
    object Loading : NetworkState<Nothing>()
    object Empty : NetworkState<Nothing>()

    data class Success<out T : Any>(val result: T) : NetworkState<T>()
    data class Error(val error: String) : NetworkState<Nothing>()
}