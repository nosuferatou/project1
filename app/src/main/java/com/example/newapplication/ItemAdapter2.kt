package com.example.newapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapplication.model.Item
import com.example.newapplication.model.Item2


class ItemAdapter2(private val ItemList2: List<Item2>) :
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
        holder.MovieName.text = item.movietitle
        holder.MovieGenre.text = item.genre
        holder.MoviePoster.setImageResource(item.movieposter)

    }

    override fun getItemCount() = ItemList2.size
    }
