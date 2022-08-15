package com.example.tbctask15

sealed class Handler<T> {
    data class Success<T>(val data: T) : Handler<T>()
    data class Error<T>(val errorData: String):Handler<T>()
    data class Loading<T>(val loader: Boolean) : Handler<T>()

}