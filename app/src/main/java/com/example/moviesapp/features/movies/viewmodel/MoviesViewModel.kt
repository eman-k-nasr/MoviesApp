package com.example.moviesapp.features.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.model.Result
import com.example.moviesapp.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(val repo: MoviesRepository) : ViewModel() {

    fun getPopularMovies(
        lang: String = "en-US",
        page: String = "1"
    ): LiveData<Result> {
        val popularMovies: LiveData<Result> = MutableLiveData()
        viewModelScope.launch {
            (popularMovies as MutableLiveData).value =
                repo.getPopularMovies(lang = lang, page = page)
        }
        return popularMovies
    }

    fun searchMovie(query: String): LiveData<Result> {
        val filteredMovies: LiveData<Result> = MutableLiveData()
        viewModelScope.launch {
            (filteredMovies as MutableLiveData).value = repo.searchMovies(query = query)
        }
        return filteredMovies
    }

}