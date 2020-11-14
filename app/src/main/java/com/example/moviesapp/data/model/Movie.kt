package com.example.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Long,
    @SerializedName("total_results")
    val totalResults: Long,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("results")
    val movies: List<Movie>
)

data class Movie(
    val popularity: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    val video: Boolean,
    @SerializedName("poster_path")
    val posterPath: String,
    val id: Long,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("original_language")
    val originalLanguage: OriginalLanguage,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("genre_ids")
    val genreIDS: List<Long>,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String
)

enum class OriginalLanguage {
    En,
    Fr,
    Ja,
    Ko
}

