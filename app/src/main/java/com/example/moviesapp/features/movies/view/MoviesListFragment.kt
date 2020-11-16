package com.example.moviesapp.features.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.data.local.LocalDatabaseImp
import com.example.moviesapp.data.local.MovieDatabase
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.model.Result
import com.example.moviesapp.data.remote.RetrofitBuilder
import com.example.moviesapp.data.repository.MoviesRepository
import com.example.moviesapp.features.movies.adapter.MoviesAdapter
import com.example.moviesapp.features.movies.viewmodel.MoviesViewModel
import com.example.moviesapp.utils.Connectivity
import com.example.moviesapp.utils.MoviesViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment() {
    private lateinit var moviesViewModel : MoviesViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    lateinit var connectivity: Connectivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = MoviesViewModelFactory(
            MoviesRepository(
                RetrofitBuilder.apiService
                , LocalDatabaseImp(MovieDatabase.DatabaseBuilder.getInstance(requireContext()))
            )
        )
        moviesViewModel = ViewModelProvider(this,viewModelFactory).get(MoviesViewModel::class.java)
        moviesAdapter = MoviesAdapter(listOf())
        connectivity = Connectivity(requireContext())
        moviesListRecyclerView.layoutManager = LinearLayoutManager(context)
        moviesListRecyclerView.adapter = moviesAdapter
    }

    override fun onResume() {
        super.onResume()
        Connectivity(requireContext()).observe(this, Observer { isConnected ->
            isConnected?.let {
                if (it) {
                    showProgressBar()
                    moviesViewModel.getPopularMovies().observe(viewLifecycleOwner, Observer {
                        handleResult(it)
                    })
                    moviesViewModel.searchMovie(query = "ant man")
                        .observe(viewLifecycleOwner, Observer {
                            //todo:implement search feature
                        })
                } else {
                    moviesViewModel.getLocalMovies().observe(viewLifecycleOwner, Observer {
                        handleResult(it)
                    })
                }
            }
        })

    }

    fun handleResult(result: Result){
        return when(result){
            is Result.Success<*> -> showResult(result.data)
            is Result.Error -> handleError(result.error)
            Result.InvalidData -> showEmptyView()
            is Result.NetworkException -> handleError(result.error)
            is Result.HttpErrors.ResourceNotFound -> handleError(result.exception)
            is Result.HttpErrors.InternalServerError -> handleError(result.exception)
        }
    }

    fun showProgressBar(){
        progressBar.visibility = VISIBLE
        imageView.visibility = INVISIBLE
        textView.visibility = INVISIBLE
        moviesListRecyclerView.visibility = INVISIBLE
    }
    fun showEmptyView(){
        progressBar.visibility = INVISIBLE
        emptygroup.visibility = VISIBLE
        moviesListRecyclerView.visibility = INVISIBLE
        imageView.setImageResource(R.drawable.ic_invalid)
        textView.text = getString(R.string.invalid)
    }
    fun showResult(data: Any?){
        progressBar.visibility = INVISIBLE
        emptygroup.visibility = INVISIBLE
        moviesListRecyclerView.visibility = VISIBLE
        moviesAdapter = MoviesAdapter(data as List<Movie>)
        moviesListRecyclerView.adapter = moviesAdapter
    }
    fun handleError(msg:String){
        progressBar.visibility = INVISIBLE
        emptygroup.visibility = VISIBLE
        moviesListRecyclerView.visibility = INVISIBLE
        imageView.setImageResource(R.drawable.ic_error)
        textView.text = msg
    }
}
