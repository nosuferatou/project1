package com.example.newapplication

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newapplication.databinding.SqliteActivityBinding
import com.example.newapplication.db.DatabaseHelper
import com.example.newapplication.model.Movie

class TestSqliteActivity : AppCompatActivity() {

    private lateinit var binding: SqliteActivityBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SqliteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        val movie: Movie? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("MOVIE", Movie::class.java)
        } else {
            intent.getParcelableExtra("MOVIE")
        }

        if (movie != null) {
            dbHelper.insertMovie(movie)
        }

        binding.buttonLoad.setOnClickListener {
            val movies = dbHelper.getMovies()
            binding.textViewMovies.text = movies.joinToString("\n") { it.title }
        }
    }

}