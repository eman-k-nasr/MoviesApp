package com.example.moviesapp.features.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.model.Result
import com.example.moviesapp.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class DetailsViewModel(val repo: MoviesRepository) : ViewModel()  {

    fun getMovieDetail(id: Int): LiveData<Result> {
        val movieDetails: LiveData<Result> = MutableLiveData()
        viewModelScope.launch {
            (movieDetails as MutableLiveData).value = repo.getMovieDetails(id= id)
        }
        return movieDetails
    }

}