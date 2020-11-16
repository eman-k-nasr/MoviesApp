package com.example.moviesapp.data.local

import com.example.moviesapp.data.model.Movie

interface ILocalDatabase {
    suspend fun getMovies(): List<Movie>
    suspend fun insertMovies(movies: List<Movie>)
}