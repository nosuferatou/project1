package com.example.newapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieListModel(val results: MutableList<Movie>) : Parcelable

@Parcelize
data class Movie(
    val id: Int,
    val poster_path: String,
    val title: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val overview: String
) : Parcelable