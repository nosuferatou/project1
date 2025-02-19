package com.example.newapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapplication.model.Item


class ItemAdapter(private val itemlist: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ProfileName: TextView = view.findViewById(R.id.usernama)
        val ProfileAge: TextView = view.findViewById(R.id.userumur)
        val ProfilePict: ImageView = view.findViewById(R.id.userpict)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemlist[position]
        holder.ProfileName.text = item.name
        holder.ProfileAge.text = item.age.toString()
        holder.ProfilePict.setImageResource(item.userpict)
        R.id.nav_search

    }

    override fun getItemCount() = itemlist.size
    }
