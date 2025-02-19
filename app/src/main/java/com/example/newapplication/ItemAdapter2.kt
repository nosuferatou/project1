package com.example.newapplication

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
import com.example.newapplication.model.Item
import com.example.newapplication.model.Item2
import com.example.newapplication.model.Movie


class ItemAdapter2(private val ItemList2: List<Movie>) :
    RecyclerView.Adapter<ItemAdapter2.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val MovieName: TextView = view.findViewById(R.id.movietitle)
        val MovieGenre: TextView = view.findViewById(R.id.moviegenre)
        val MoviePoster: ImageView = view.findViewById(R.id.moviepict)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list2, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = ItemList2[position]
        holder.MovieName.text = item.title
        holder.MovieGenre.text = ""
        holder.MoviePoster.loadFromUrl(Constants.IMAGE_PATH + item.poster_path.substring(1))
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

    override fun getItemCount() = ItemList2.size

    }

