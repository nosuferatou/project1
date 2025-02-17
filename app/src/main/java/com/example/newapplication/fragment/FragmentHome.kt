package com.example.newapplication.fragment

import android.content.ClipData
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.ItemAdapter
import com.example.newapplication.databinding.FragmentHomeBinding
import com.example.newapplication.model.Item
import com.example.newapplication.R



class FragmentHome: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // membuat variabel untuk recycleview yang ada di layout fragment_home.xml
        val recycleview = binding.recycleView

        val userListItem = listOf(
            Item("Fernando", 21, R.drawable.profile),
            Item("Jose", 30, R.drawable.img),
            Item("Adam", 24, R.drawable.pp2),
            Item("Joko", 19, R.drawable.profile)
        )

        // set recycleview
        recycleview.layoutManager = LinearLayoutManager(requireContext())
        recycleview.adapter = ItemAdapter(userListItem)


    }
}
