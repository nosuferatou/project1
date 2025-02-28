package com.example.newapplication.ItemAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.newapplication.Constants
import com.example.newapplication.R
import com.example.newapplication.db.DatabaseHelper
import com.example.newapplication.model.Genres
import com.example.newapplication.model.Movie


class AdapterFavorites(private val favMovie: List<Movie>, private val genres: Genres, private val onItemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<AdapterFavorites.ItemViewHolder>() {
//        private lateinit var utilityClass: DatabaseHelper

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val MovieName: TextView = view.findViewById(R.id.movietitle)
        val MovieGenre: TextView = view.findViewById(R.id.moviegenre)
        val MoviePoster: ImageView = view.findViewById(R.id.moviepict)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_favorites, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = favMovie[position]
        holder.MovieName.text = item.title

        val genresArr = item.genre_ids.mapNotNull { genreId ->
            genres.genres.find { it.id == genreId }?.name
        }.joinToString(", ")

        holder.MovieGenre.text = genresArr
        holder.MoviePoster.loadFromUrl(Constants.IMAGE_PATH + item.poster_path.substring(1))
        holder.itemView.setOnClickListener {
            onItemClick(item)
//            utilityClass.getMovies()
        }
    }

    fun ImageView.loadFromUrl(imageUrl: String?) {
        val options = RequestOptions()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .priority(Priority.IMMEDIATE)
        Glide.with(this.context)
            .load(imageUrl ?: "")
            .apply(options)
            .centerCrop()
            .into(this)
    }

    override fun getItemCount() = favMovie.size

}

