package com.example.newapplication.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newapplication.Constants
import com.example.newapplication.ItemAdapter.AdapterNowPlaying
import com.example.newapplication.ItemAdapter.AdapterPopMovie
import com.example.newapplication.ItemAdapter.AdapterTopRated
import com.example.newapplication.MovieDetailActivity
import com.example.newapplication.databinding.FragmentHomeBinding
import com.example.newapplication.model.Genres
import com.example.newapplication.model.MovieListModel
import com.example.newapplication.service.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(space, space, space, space)
    }
}

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var currentPage = 1
    private var isLoading = false
    private var totalPages = 1
    private lateinit var topRatedAdapter : AdapterTopRated

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPopularMovie()
        getNowPlayingMovie()
        getTopRated(currentPage)
        setOnBottomScroll()
    }

    private fun setOnBottomScroll() {
        binding.svParent.viewTreeObserver.addOnScrollChangedListener {
            val scrollView = binding.svParent
            val view = scrollView.getChildAt(scrollView.childCount - 1)

            val diff = view.bottom - (scrollView.height + scrollView.scrollY)

            if (diff == 0 && !isLoading && currentPage < totalPages) {
                isLoading = true
                currentPage++
                loadMoreMovies()
            }
        }
    }

    private fun loadMoreMovies() {
        getTopRated(currentPage)
    }

    private fun getPopularMovie() {
        RetrofitClient.instance.getPopularMovies(Constants.API_KEY).enqueue(object : Callback<MovieListModel> {
            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                Log.e("Retrofit", "Error: ${t.message}")
            }

            override fun onResponse(call: Call<MovieListModel>, response: Response<MovieListModel>) {
                if (response.isSuccessful) {
                    response.body()?.let { setPopularRv(it) }
                }
            }
        })
    }

    private fun getNowPlayingMovie() {
        RetrofitClient.instance.getNowPlayingMovies(Constants.API_KEY).enqueue(object : Callback<MovieListModel> {
            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                Log.e("Retrofit", "Error: ${t.message}")
            }

            override fun onResponse(call: Call<MovieListModel>, response: Response<MovieListModel>) {
                if (response.isSuccessful) {
                    response.body()?.let { setNowPlayingRv(it) }
                }
            }
        })
    }

    private fun getTopRated(page: Int = 1) {
        RetrofitClient.instance.getTopRated(Constants.API_KEY, page).enqueue(object : Callback<MovieListModel> {
            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                Log.e("Retrofit", "Error: ${t.message}")
                isLoading = false
            }

            override fun onResponse(call: Call<MovieListModel>, response: Response<MovieListModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        isLoading = false
                        totalPages = it.total_pages
                        if (currentPage == 1) {
                            setTopRatedRv(it)
                        }
                        else {
                            topRatedAdapter.addMovies(it.results)
                            setRecyclerViewHeightForGrid(binding.topRatedRv, 3)
                        }
                    }
                }
            }
        })
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }

    private fun setPopularRv(listModel: MovieListModel) {
        val popularMovie = binding.popularMovieRv
        popularMovie.addItemDecoration(SpaceItemDecoration(10))
        popularMovie.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val genreResponse = Gson().fromJson(getJsonDataFromAsset(requireContext(), "genre.json"), Genres::class.java)
        popularMovie.adapter = AdapterPopMovie(listModel.results, genreResponse) { selectedMovie ->
            startActivity(Intent(context, MovieDetailActivity::class.java).apply {
                putExtra("MOVIE", selectedMovie)
            })
            Toast.makeText(requireContext(), "Clicked: ${selectedMovie.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setNowPlayingRv(listModel: MovieListModel) {
        val nowPlaying = binding.nowPlayingRv
        nowPlaying.addItemDecoration(SpaceItemDecoration(10))
        nowPlaying.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val genreResponse = Gson().fromJson(getJsonDataFromAsset(requireContext(), "genre.json"), Genres::class.java)
        nowPlaying.adapter = AdapterNowPlaying(listModel.results, genreResponse) { selectedMovie ->
            startActivity(Intent(context, MovieDetailActivity::class.java).apply {
                putExtra("MOVIE", selectedMovie)
            })
            Toast.makeText(requireContext(), "Clicked: ${selectedMovie.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setTopRatedRv(listModel: MovieListModel) {
        val topRated = binding.topRatedRv
        topRated.addItemDecoration(SpaceItemDecoration(10))
        topRated.layoutManager = GridLayoutManager(requireContext(), 3)
        val genreResponse = Gson().fromJson(getJsonDataFromAsset(requireContext(), "genre.json"), Genres::class.java)
        topRatedAdapter = AdapterTopRated(listModel.results, genreResponse) { selectedMovie ->
            startActivity(Intent(context, MovieDetailActivity::class.java).apply {
                putExtra("MOVIE", selectedMovie)
            })
            Toast.makeText(requireContext(), "Clicked: ${selectedMovie.title}", Toast.LENGTH_SHORT).show()
        }
        topRated.adapter = topRatedAdapter
        setRecyclerViewHeightForGrid(topRated, 3)
    }

    private fun setRecyclerViewHeightForGrid(recyclerView: RecyclerView, spanCount: Int) {
        recyclerView.post {
            val adapter = recyclerView.adapter ?: return@post
            val itemCount = adapter.itemCount
            if (itemCount == 0) return@post

            val holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(0))
            adapter.onBindViewHolder(holder, 0)

            holder.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(recyclerView.width / spanCount, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.UNSPECIFIED
            )
            val itemHeight = holder.itemView.measuredHeight
            val rows = (itemCount + spanCount - 1) / spanCount
            val params = recyclerView.layoutParams
            params.height = (rows * itemHeight) + itemHeight
            recyclerView.layoutParams = params
            recyclerView.requestLayout()
        }
    }
}
