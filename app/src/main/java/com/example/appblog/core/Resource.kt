package com.example.appblog.core

import java.lang.Exception

sealed class Result<out T> {
    // Estado de carga
    class Loading<out T> : Result<T>()

    // Estado de éxito, que contiene los datos resultantes
    data class Success<out T>(val data: T) : Result<T>()

    // Estado de fallo, que contiene una excepción
    data class Failure(val exception: Exception) : Result<Nothing>()
}
