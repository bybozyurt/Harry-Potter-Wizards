package com.cevlikalprn.harrypotterwizards.util

sealed class NetworkResult<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(data: T? = null, errorMessage: String?) : NetworkResult<T>(data, errorMessage)
    class Loading<T> : NetworkResult<T>()
}