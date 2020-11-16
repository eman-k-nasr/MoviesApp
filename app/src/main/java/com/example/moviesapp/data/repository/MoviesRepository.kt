package com.example.moviesapp.data.repository

import com.example.moviesapp.data.local.ILocalDatabase
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.model.Result
import com.example.moviesapp.data.remote.MoviesApiService
import java.io.IOException

class MoviesRepository(val remote: MoviesApiService, val local: ILocalDatabase) {

    suspend fun getPopularMovies(
        lang: String = "en-US",
        page: String = "1"
    ): Result {
        return try {
            val response = remote.getPopularMovies(lang = lang, page = page)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    insertMovies(response.body()!!.movies)
                    Result.Success(data = response.body()?.movies)
                } else {
                    Result.InvalidData
                }
            } else {
                when (response.code()) {
                    404 -> Result.HttpErrors.ResourceNotFound(response.message())
                    500 -> Result.HttpErrors.InternalServerError(response.message())
                    else -> Result.NetworkException(response.message())
                }
            }
        } catch (ex: IOException) {
            Result.Error(error = ex.message!!)
        }
    }

    suspend fun searchMovies(
        query: String
    ): Result {
        return try {
            val response = remote.searchMovie(query = query)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    Result.Success(data = response.body()?.movies)
                } else {
                    Result.InvalidData
                }
            } else {
                when (response.code()) {
                    404 -> Result.HttpErrors.ResourceNotFound(response.message())
                    500 -> Result.HttpErrors.InternalServerError(response.message())
                    else -> Result.NetworkException(response.message())
                }
            }
        } catch (ex: IOException) {
            Result.Error(error = ex.message!!)
        }
    }

    suspend fun getMovieDetails(
        id: Int
    ): Result {
        return try {
            val response = remote.getMovieDetails(id = id)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    Result.Success(data = response.body())
                } else {
                    Result.InvalidData
                }
            } else {
                when (response.code()) {
                    404 -> Result.HttpErrors.ResourceNotFound(response.message())
                    500 -> Result.HttpErrors.InternalServerError(response.message())
                    else -> Result.NetworkException(response.message())
                }
            }
        } catch (ex: IOException) {
            Result.Error(error = ex.message!!)
        }
    }

    suspend fun getLocalMovies(): Result {
        val movies = local.getMovies()
        if (movies.isEmpty()) {
            return Result.InvalidData
        } else {
            return Result.Success(movies)
        }
    }

    suspend fun insertMovies(movies: List<Movie>) {
        local.insertMovies(movies)
    }

}