package com.example.moviesapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.data.repository.MoviesRepository
import com.example.moviesapp.features.movies.viewmodel.MoviesViewModel

class MoviesViewModelFactory(var repo:MoviesRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesViewModel::class.java)){
            return MoviesViewModel(repo) as T
        }
        throw IllegalArgumentException("viewmodel class is not found")
    }

}