package com.example.newapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.ItemAdapter
import com.example.newapplication.ItemAdapter2
import com.example.newapplication.databinding.FragmentHomeBinding
import com.example.newapplication.model.Item
import com.example.newapplication.R
import com.example.newapplication.model.Item2


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

        // function
        setPopularRv()
        setTopRv()
    }

    // membuat function
    private  fun setPopularRv(){
        // membuat variabel untuk recycleview yang ada di layout fragment_home.xml
        val recycleview = binding.recycleView

        val userListItem = listOf(
            Item("Fernando", 21, R.drawable.profile),
            Item("Jose", 30, R.drawable.img),
            Item("Adam", 24, R.drawable.pp2),
            Item("Joko", 19, R.drawable.profile)
        )

        // set recycleview
        recycleview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycleview.adapter = ItemAdapter(userListItem)
    }
    private  fun setTopRv(){
        // membuat variabel untuk recycleview yang ada di layout fragment_home.xml
        val recycleview2 = binding.recycleView2

        val movieList = listOf(
            Item2("Heil Fuhrer", "Historical, Action", R.drawable.img),
            Item2("Burning Hall", "Action, Thriller", R.drawable.pp2),
            Item2("Bladerunner 2069", "Sci-Fi, Action", R.drawable.real),
            Item2("Five Night at Cio's", "Horror, Thriller", R.drawable.fnaf),
            Item2("Devil May Cry", "Action, Sci-Fi", R.drawable.dmc),
            Item2("Heil Fuhrer", "Historical, Action", R.drawable.img),
            Item2("Burning Hall", "Action, Thriller", R.drawable.pp2),
            Item2("Bladerunner 2069", "Sci-Fi, Action", R.drawable.real),
            Item2("Five Night at Cio's", "Horror, Thriller", R.drawable.fnaf),
            Item2("Devil May Cry", "Action, Sci-Fi", R.drawable.dmc)
        )

        // set recycleview
        recycleview2.layoutManager = GridLayoutManager(requireContext(),3)
        recycleview2.adapter = ItemAdapter2(movieList)
    }
}
