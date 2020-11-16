package com.example.moviesapp.data.local

import com.example.moviesapp.data.model.Movie

class LocalDatabaseImp(private val db: MovieDatabase) : ILocalDatabase {
    override suspend fun getMovies(): List<Movie> = db.movieDao().getMovies()
    override suspend fun insertMovies(movies: List<Movie>) = db.movieDao().insertMovies(movies)
}