package com.example.moviesapp.features.movies.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.remote.RetrofitBuilder
import com.example.moviesapp.data.repository.MoviesRepository
import com.example.moviesapp.features.movies.adapter.MoviesAdapter
import com.example.moviesapp.features.movies.viewmodel.MoviesViewModel
import com.example.moviesapp.utils.API_KEY
import com.example.moviesapp.utils.MoviesViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment() {
    private lateinit var moviesViewModel : MoviesViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var movies : List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = MoviesViewModelFactory(MoviesRepository(RetrofitBuilder.apiService))
        moviesViewModel = ViewModelProvider(this,viewModelFactory).get(MoviesViewModel::class.java)
        moviesAdapter = MoviesAdapter(listOf())
        moviesListRecyclerView.layoutManager = LinearLayoutManager(context)
        moviesListRecyclerView.adapter = moviesAdapter
        progressBar.visibility = VISIBLE
        moviesListRecyclerView.visibility = INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        moviesViewModel.getPopularMovies().observe(viewLifecycleOwner, Observer {
            Log.i("MSG", "size of popular movies is ${it?.size}")
            //todo:handle result in a proper way
            movies = it!!
            moviesAdapter = MoviesAdapter(movies)
            moviesListRecyclerView.adapter = moviesAdapter
            moviesListRecyclerView.visibility = VISIBLE
            progressBar.visibility = INVISIBLE
        })
        moviesViewModel.searchMovie(query= "ant man").observe(viewLifecycleOwner, Observer {
            Log.i("MSG", "size of filtered movies is ${it?.size}")
        })
    }
}
