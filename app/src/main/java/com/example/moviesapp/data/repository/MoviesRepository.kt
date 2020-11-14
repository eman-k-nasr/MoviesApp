package com.example.moviesapp.data.repository

import com.example.moviesapp.data.model.Details
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.remote.MoviesApiService

class MoviesRepository(val remote: MoviesApiService) {

    suspend fun getPopularMovies(
        lang: String = "en-US",
        page: String = "1"
    ): List<Movie>? {
        return remote.getPopularMovies(lang=lang, page = page).body()?.movies
    }

    suspend fun searchMovies(
        query: String
    ): List<Movie>? {
        return remote.searchMovie(query = query).body()?.movies
    }

    suspend fun getMovieDetails(
        id: Int
    ): Details? {
        return remote.getMovieDetails(id= id).body()
    }

}