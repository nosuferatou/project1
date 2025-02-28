package com.example.newapplication.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.Constants
import com.example.newapplication.ItemAdapter.AdapterFavorites
import com.example.newapplication.ItemAdapter.AdapterPopMovie
import com.example.newapplication.MovieDetailActivity
import com.example.newapplication.databinding.FragmentFavoritesBinding
import com.example.newapplication.db.DatabaseHelper
import com.example.newapplication.model.Genres
import com.example.newapplication.model.Movie
import com.example.newapplication.model.MovieListModel
import com.example.newapplication.service.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FragmentFavorites: Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var dbhelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbhelper = DatabaseHelper(requireContext())
        binding.favMovieRv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // Load favorite movies
        loadFavoriteMovies()
    }

    override fun onResume() {
        super.onResume()
        // Reload favorites when returning to this fragment
        loadFavoriteMovies()
    }

    private fun loadFavoriteMovies() {
        // Get favorite movies from database
        val favoriteMovies = dbhelper.getMovies()

        if (favoriteMovies.isNotEmpty()) {
            val movieListModel = MovieListModel(results = favoriteMovies.toMutableList())
            setFavMovie(movieListModel)

            // Show RecyclerView, hide empty state if you have one
            binding.favMovieRv.visibility = View.VISIBLE
            // binding.emptyStateView?.visibility = View.GONE
        } else {
            // Hide RecyclerView, show empty state
            binding.favMovieRv.visibility = View.GONE
            // binding.emptyStateView?.visibility = View.VISIBLE
        }
    }


    private  fun setFavMovie(listModel: MovieListModel){
        // membuat variabel untuk recycleview yang ada di layout fragment_favorites.xml
        val favMovie = binding.favMovieRv

        favMovie.addItemDecoration(SpaceItemDecoration(10))

        // set recycleview
        favMovie.layoutManager = GridLayoutManager(requireContext(), 2)
        val genreStr = getJsonDataFromAsset(requireContext(),"genre.json")
        val genreResponse = Gson().fromJson(genreStr, Genres::class.java)

        // set adapter
        favMovie.adapter = AdapterFavorites(listModel.results, genreResponse) { selectedMovie ->
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra("MOVIE", selectedMovie)
            }
            startActivity(intent)
            Toast.makeText(requireContext(), "Clicked: ${selectedMovie.title}", Toast.LENGTH_SHORT).show()
        }
    }




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