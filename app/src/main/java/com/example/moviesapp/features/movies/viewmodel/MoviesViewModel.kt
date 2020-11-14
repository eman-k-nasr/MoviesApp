package com.example.moviesapp.features.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.model.Details
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(val repo: MoviesRepository) : ViewModel() {

    fun getPopularMovies(
        lang: String = "en-US",
        page: String = "1"
    ): LiveData<List<Movie>?> {
        val popularMovies: LiveData<List<Movie>?> = MutableLiveData()
        viewModelScope.launch {
            (popularMovies as MutableLiveData).value = repo.getPopularMovies(lang= lang,page= page)
        }
        return popularMovies
    }

    fun searchMovie(query: String): LiveData<List<Movie>?> {
        val filteredMovies: LiveData<List<Movie>?> = MutableLiveData()
        viewModelScope.launch {
            (filteredMovies as MutableLiveData).value = repo.searchMovies(query= query)
        }
        return filteredMovies
    }

    fun getMovieDetail(id: Int): LiveData<Details> {
        val movieDetails: LiveData<Details> = MutableLiveData()
        viewModelScope.launch {
            (movieDetails as MutableLiveData).value = repo.getMovieDetails(id= id)
        }
        return movieDetails
    }
}