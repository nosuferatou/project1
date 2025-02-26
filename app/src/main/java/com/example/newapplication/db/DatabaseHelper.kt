package com.example.newapplication.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcelable
import com.example.newapplication.model.Movie
import kotlinx.parcelize.Parcelize

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "MovieDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE movies (" +
                    "id INTEGER PRIMARY KEY, " +
                    "poster_path TEXT, " +
                    "title TEXT, " +
                    "release_date TEXT, " +
                    "genre_ids TEXT, " +
                    "overview TEXT, " +
                    "vote_average REAL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS movies")
        onCreate(db)
    }

    fun insertMovie(movie: Movie) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id", movie.id)
            put("poster_path", movie.poster_path)
            put("title", movie.title)
            put("release_date", movie.release_date)
            put("genre_ids", movie.genre_ids.joinToString(",")) // Simpan List<Int> sebagai String
            put("overview", movie.overview)
            put("vote_average", movie.vote_average)
        }
        db.insertWithOnConflict("movies", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun getMovies(): List<Movie> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM movies", null)
        val movies = mutableListOf<Movie>()

        while (cursor.moveToNext()) {
            val genreString = cursor.getString(4)
            val genreList = genreString.split(",").mapNotNull { it.toIntOrNull() }

            val movie = Movie(
                id = cursor.getInt(0),
                poster_path = cursor.getString(1),
                title = cursor.getString(2),
                release_date = cursor.getString(3),
                genre_ids = genreList,
                overview = cursor.getString(5),
                vote_average = cursor.getFloat(6)
            )
            movies.add(movie)
        }

        cursor.close()
        db.close()
        return movies
    }
}