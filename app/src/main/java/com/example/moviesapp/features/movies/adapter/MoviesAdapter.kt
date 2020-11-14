package com.example.moviesapp.features.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.utils.IMAGE_BASE_URL
import com.squareup.picasso.Picasso


class MoviesAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        (MovieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.movie_item_layout, parent, false)
        ))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieName.text = movies[position].title
        holder.movieOverview.text = movies[position].overview
        holder.movieReleaseDate.text = movies[position].releaseDate
        Picasso.get().load("${IMAGE_BASE_URL}${movies[position].backdropPath}")
            .into(holder.movieImg)
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieImg: ImageView = view.findViewById(R.id.movieImageView)
        val movieName: TextView = view.findViewById(R.id.movieNameTextView)
        val movieOverview: TextView = view.findViewById(R.id.movieOverviewTextView)
        val movieReleaseDate: TextView = view.findViewById(R.id.movieReleaseDateTextView)
    }


}