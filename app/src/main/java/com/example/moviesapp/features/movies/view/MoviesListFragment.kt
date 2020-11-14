package com.example.moviesapp.features.movies.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.R
import com.example.moviesapp.data.remote.RetrofitBuilder
import com.example.moviesapp.data.repository.MoviesRepository
import com.example.moviesapp.features.movies.viewmodel.MoviesViewModel
import com.example.moviesapp.utils.API_KEY
import com.example.moviesapp.utils.MoviesViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment() {
    lateinit var moviesViewModel : MoviesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory = MoviesViewModelFactory(MoviesRepository(RetrofitBuilder.apiService))
        moviesViewModel = ViewModelProvider(this,viewModelFactory).get(MoviesViewModel::class.java)
        moviesViewModel.getPopularMovies().observe(viewLifecycleOwner, Observer {
            Log.i("MSG", "size of movies is ${it?.size}")
        })
        moviesViewModel.searchMovie(query= "ant man").observe(viewLifecycleOwner, Observer {
            Log.i("MSG", "size of movies is ${it?.size}")
        })
        moviesViewModel.getMovieDetail(id= 80).observe(viewLifecycleOwner, Observer {
            Log.i("MSG", "movie info: ${it}")
        })
    }
}
