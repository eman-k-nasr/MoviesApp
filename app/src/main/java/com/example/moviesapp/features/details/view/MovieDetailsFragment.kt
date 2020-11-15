package com.example.moviesapp.features.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.R
import com.example.moviesapp.data.model.Details
import com.example.moviesapp.data.model.Result
import com.example.moviesapp.data.remote.RetrofitBuilder
import com.example.moviesapp.data.repository.MoviesRepository
import com.example.moviesapp.features.details.viewmodel.DetailsViewModel
import com.example.moviesapp.features.movies.adapter.GenresAdapter
import com.example.moviesapp.utils.IMAGE_BASE_URL
import com.example.moviesapp.utils.MoviesViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movies_list.progressBar

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var genresAdapter: GenresAdapter

    lateinit var detailsViewModel : DetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genresAdapter = GenresAdapter(listOf(),requireContext())
        val viewModelFactory = MoviesViewModelFactory(MoviesRepository(RetrofitBuilder.apiService))
        detailsViewModel = ViewModelProvider(this,viewModelFactory).get(DetailsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        showProgressBar()
        detailsViewModel.getMovieDetail(args.movieId).observe(viewLifecycleOwner, Observer {
            handleResult(it)
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
        progressBar.visibility = View.VISIBLE
    }
    fun showEmptyView(){
        println("No Data is Found")
    }
    fun showResult(data: Any?){
        progressBar.visibility = View.INVISIBLE
        val movie = data as Details
        setViews(movie)
    }
    fun handleError(msg:String){
        progressBar.visibility = View.INVISIBLE
        println("ERROR: ${msg}")
    }
    fun setViews(movie:Details){
        movieNameTv.text = movie.title
        movieOverviewTv.text = movie.overview
        movieVoteAverageTv.text = movie.voteAverage.toString()
        movieVoteCountTv.setText("(${movie.voteCount})")
        movieReleaseDateTv.text = movie.releaseDate
        Picasso.get().load("$IMAGE_BASE_URL${movie.backdropPath}")
            .into(movieImg)
        setGridView(movie)
    }
    fun setGridView(movie:Details){
        val genre_list = movie.genres
        genresAdapter = GenresAdapter(genre_list,requireContext())
        genreGridView.numColumns = 4
        genreGridView.horizontalSpacing = 10
        genreGridView.adapter = genresAdapter

    }
}
