package com.example.newapplication

import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.newapplication.databinding.ActivityMoviedetailBinding
import com.example.newapplication.db.DatabaseHelper
import com.example.newapplication.model.Genres
import com.example.newapplication.model.Movie
import com.google.gson.Gson
import java.io.IOException


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviedetailBinding
    private lateinit var dbhelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviedetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbhelper = DatabaseHelper(this)

        val movie: Movie? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("MOVIE", Movie::class.java)
        } else {
            intent.getParcelableExtra("MOVIE")
        }

        if (movie != null) {
            updateFavoriteButtonState(movie) //check if movie already in favorites
        }

        val movieName = movie?.title
        val moviePoster = movie?.poster_path
        val movieOverview = movie?.overview
        val movieGenre: List<Int>? = movie?.genre_ids
        val genreText = movieGenre?.joinToString(", ") ?: "No genres available"
        val movieReleaseDate = movie?.release_date
//        var rating = String.format("%.1f", movie?.vote_average.toString())
//        val movieRating = rating

        val df = DecimalFormat("#.#")
        val movieRating = df.format(movie?.vote_average)

        var genreStr = getJsonDataFromAsset(this, "genre.json")
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
        binding.btnFav.setOnClickListener {
            if (movie != null) {
                val existingMovies = dbhelper.getMovies()
                val addedFavorites = existingMovies.any { it.id == movie.id }

                if (addedFavorites) {
                    dbhelper.deleteMovie(movie.id)
                    Toast.makeText(this, "Movie removed from Favorites!", Toast.LENGTH_SHORT).show()
                } else {
                    dbhelper.insertMovie(movie)
                    Toast.makeText(this, "Movie added to Favorites", Toast.LENGTH_SHORT).show()
                }
                updateFavoriteButtonState(movie) // update button state when click (jadi ketika buka activity tidak mengulang dari awal)
            } else {
                Toast.makeText(this, "Failed to update Favorites, try again!", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun updateFavoriteButtonState(movie: Movie) {
        val existingMovies = dbhelper.getMovies()
        val isFavorite = existingMovies.any { it.id == movie.id }

        binding.btnFav.text = if (isFavorite) "-" else "+"
    }

    // getJson local function
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



//private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
//        val jsonString: String
//        try {
//            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
//        } catch (ioException: IOException) {
//            ioException.printStackTrace()
//            return null
//        }
//        return jsonString
//    }



