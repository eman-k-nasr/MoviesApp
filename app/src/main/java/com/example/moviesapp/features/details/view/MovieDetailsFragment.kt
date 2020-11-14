package com.example.moviesapp.features.details.view

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
import com.example.moviesapp.features.details.viewmodel.DetailsViewModel
import com.example.moviesapp.utils.MoviesViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    lateinit var detailsViewModel : DetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory = MoviesViewModelFactory(MoviesRepository(RetrofitBuilder.apiService))
        detailsViewModel = ViewModelProvider(this,viewModelFactory).get(DetailsViewModel::class.java)
        detailsViewModel.getMovieDetail(id= 80).observe(viewLifecycleOwner, Observer {
            Log.i("MSG", "movie info: ${it}")
        })
    }
}
