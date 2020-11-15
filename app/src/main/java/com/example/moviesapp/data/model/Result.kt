package com.example.moviesapp.data.model

sealed class Result{
    data class Success<out T>(val data: T): Result()
    data class Error(val error : String) :Result()
    object InvalidData : Result()
    data class NetworkException(val error : String) : Result()
    sealed class HttpErrors : Result() {
        data class ResourceNotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
    }
}