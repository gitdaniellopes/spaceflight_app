package br.com.spaceflight.util

sealed class State<out T : Any> {
    object Loading : State<Nothing>()
    object Empty : State<Nothing>()

    data class Success<out T : Any>(val result: T) : State<T>()
    data class Error(val error: Throwable) : State<Nothing>()
}