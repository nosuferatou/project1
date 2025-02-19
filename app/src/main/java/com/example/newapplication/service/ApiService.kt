package com.example.newapplication.service

import com.example.newapplication.Constants
import com.example.newapplication.model.MovieListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    fun getPopularMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Call<MovieListModel>
}