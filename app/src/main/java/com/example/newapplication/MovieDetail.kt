package com.example.newapplication

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newapplication.databinding.ActivityMoviedetailBinding
import com.example.newapplication.model.Genres
import com.google.gson.Gson
import java.io.IOException


class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMoviedetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviedetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieName = intent.getStringExtra("MOVIE_NAME")
        val moviePoster = intent.getStringExtra("MOVIE_POSTER")
        val movieOverview = intent.getStringExtra("MOVIE_OVERVIEW")
        val movieGenre: ArrayList<Int>? = intent.getIntegerArrayListExtra("MOVIE_GENRE")
        val genreText = movieGenre?.joinToString(", ") ?: "No genres available"
        val movieReleaseDate = intent.getStringExtra("RELEASE_DATE")
        val movieRating = intent.getStringExtra("MOVIE_RATING")

        var genreStr = getJsonDataFromAsset(this,"genre.json")
        val genreResponse = Gson().fromJson(genreStr, Genres::class.java)
        var genreArr = movieGenre?.mapNotNull { genreId ->
            genreResponse.genres.find { it.id == genreId }?.name
        }?.joinToString(", ")

        binding.MovieTitleTv.text = movieName
        binding.YearTv.text = movieReleaseDate
        binding.OverviewTv.text = movieOverview
        Glide.with(this)
            .load(moviePoster) // API image URL
            .error(R.drawable.error) // Optional: show if loading fails
            .into(binding.movieposter) // ImageView
        binding.movieGenre.text = genreArr
        binding.movieRating.text = movieRating

    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}