package com.example.moviesapp.data.remote

import com.example.moviesapp.data.model.Details
import com.example.moviesapp.data.model.MoviesResponse
import com.example.moviesapp.utils.API_KEY
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY
        , @Query("language") lang: String = "en-US"
        , @Query("page") page: String = "1"
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Details>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String
    ): Response<MoviesResponse>

}

object RetrofitBuilder {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService = getRetrofit().create(MoviesApiService::class.java)

}