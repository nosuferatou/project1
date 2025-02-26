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
import com.example.newapplication.ItemAdapter.AdapterPopMovie
import com.example.newapplication.ItemAdapter.AdapterNowPlaying
import com.example.newapplication.ItemAdapter.AdapterTopRated
import com.example.newapplication.TestSqliteActivity
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
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.bottom = space // Add space at the bottom of each item
        outRect.left = space   // Add space on the left
        outRect.right = space  // Add space on the right
        outRect.top = space

        // Add space at the top only for the first item to avoid extra space at the top of the list
//        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.top = space
        }
    }


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

        // call function
        getPopularMovie()
        getNowPlayingMovie()
        getTopRated()


    }

    // membuat fungsi getPopularMovie untuk menampilkan popular movie, (harus membuat fungsi set
    // jika menggunakan recyclerview untuk logicnya & binding recyclerview serta adapternya
    private fun getPopularMovie() {
        // Contoh GET semua post
        RetrofitClient.instance.getPopularMovies(Constants.API_KEY).enqueue(object :
            Callback<MovieListModel> {
            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                Log.e("Retrofit", "Error: ${t.message}")
            }

            override fun onResponse(
                call: Call<MovieListModel>,
                response: Response<MovieListModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        setPopularRv(posts)
                    }
                }
            }
        }
        )
    }

    // get untuk mengambil data dari API, set untuk mengatur recyclerView
    private fun getNowPlayingMovie() {
        RetrofitClient.instance.getNowPlayingMovies(Constants.API_KEY).enqueue(object :
        Callback<MovieListModel> {
            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                Log.e("Retrofit", "Error: ${t.message}")
            }

            override fun onResponse(
                call: Call<MovieListModel>,
                response: Response<MovieListModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        setNowPlayingRv(posts)
                    }
                }
            }

        })
    }

    private fun getTopRated() {
        RetrofitClient.instance.getTopRated(Constants.API_KEY).enqueue(object :
            Callback<MovieListModel> {
            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                Log.e("Retrofit", "Error: ${t.message}")
            }

            override fun onResponse(
                call: Call<MovieListModel>,
                response: Response<MovieListModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        setTopRatedRv(posts) // recyclerView
                    }
                }
            }

        })
    }

    // membuat fungsi untuk mengambil data dari Json lokal
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


    // function set RecyclerView Popular Movie
    private  fun setPopularRv(listModel: MovieListModel){
        // membuat variabel untuk recycleview yang ada di layout fragment_home.xml
        val popularMovie = binding.popularMovieRv
        popularMovie.addItemDecoration(SpaceItemDecoration(10))

        // set recycleview
        popularMovie.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val genreStr = getJsonDataFromAsset(requireContext(),"genre.json")
        val genreResponse = Gson().fromJson(genreStr, Genres::class.java)

        // logika select item from recycleview (use adapter)
        popularMovie.adapter = AdapterPopMovie(listModel.results, genreResponse) { selectedMovie ->
                val intent = Intent(context, TestSqliteActivity::class.java).apply {
                    putExtra("MOVIE", selectedMovie)
                }
                startActivity(intent)
            Toast.makeText(requireContext(), "Clicked: ${selectedMovie.title}", Toast.LENGTH_SHORT).show()
        }
    }

    // set RecyclerView Now Playing
    private fun setNowPlayingRv(listModel: MovieListModel) {
        val nowPlaying = binding.nowPlayingRv
        nowPlaying.addItemDecoration(SpaceItemDecoration(10))
        nowPlaying.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // get Data from local Json
        val genreStr = getJsonDataFromAsset(requireContext(),"genre.json")
        val genreResponse = Gson().fromJson(genreStr, Genres::class.java)

        // set adapter and select item logic
        nowPlaying.adapter = AdapterNowPlaying(listModel.results, genreResponse) { selectedMovie ->
            val intent = Intent(context, TestSqliteActivity::class.java).apply {
                putExtra("MOVIE", selectedMovie)
            }
            startActivity(intent)
            Toast.makeText(requireContext(), "Clicked: ${selectedMovie.title}", Toast.LENGTH_SHORT).show()
        }
    }

    // set RecyclerView Top Rated
    private fun setTopRatedRv(listModel: MovieListModel) {
        val topRated = binding.topRatedRv
        topRated.addItemDecoration(SpaceItemDecoration(10))

        topRated.layoutManager = GridLayoutManager(requireContext(), 3)

        // get Data from local Json
        val genreStr = getJsonDataFromAsset(requireContext(),"genre.json")
        val genreResponse = Gson().fromJson(genreStr, Genres::class.java)

        // set adapter and select item logic
        topRated.adapter = AdapterTopRated(listModel.results, genreResponse) { selectedMovie ->
            val intent = Intent(context, TestSqliteActivity::class.java).apply {
                putExtra("MOVIE", selectedMovie)
            }
            startActivity(intent)
            Toast.makeText(requireContext(), "Clicked: ${selectedMovie.title}", Toast.LENGTH_SHORT).show()
        }
        setRecyclerViewHeightForGrid(topRated, 3)

    }

        }



    // membuat function
//    private  fun setNowPlaying(){
//        // membuat variabel untuk recycleview yang ada di layout fragment_home.xml
//        val recycleViewNowPlaying = binding.recycleViewNowPlaying
//
//        val userListItem = listOf(
//            Item("Fernando", 21, R.drawable.profile),
//            Item("Jose", 30, R.drawable.img),
//            Item("Adam", 24, R.drawable.pp2),
//            Item("Joko", 19, R.drawable.profile),
//            Item("Jose", 30, R.drawable.img),
//            Item("Adam", 24, R.drawable.pp2),
//            Item("Joko", 19, R.drawable.profile)
//
//        )
//
//        // set recycleview
//        recycleViewNowPlaying.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recycleViewNowPlaying.adapter = ItemAdapter(userListItem)
//    }
//    private  fun setTopRv(){
//        // membuat variabel untuk recycleview yang ada di layout fragment_home.xml
//        val recycleview2 = binding.recycleView2
//
//        val movieList = listOf(
//            Item2("Heil Fuhrer", "Historical, Action", R.drawable.img),
//            Item2("Burning Hall", "Action, Thriller", R.drawable.pp2),
//            Item2("Bladerunner 2069", "Sci-Fi, Action", R.drawable.real),
//            Item2("Five Night at Cio's", "Horror, Thriller", R.drawable.fnaf),
//            Item2("Devil May Cry", "Action, Sci-Fi", R.drawable.dmc),
//            Item2("Heil Fuhrer", "Historical, Action", R.drawable.img),
//            Item2("Burning Hall", "Action, Thriller", R.drawable.pp2),
//            Item2("Bladerunner 2069", "Sci-Fi, Action", R.drawable.real),
//            Item2("Five Night at Cio's", "Horror, Thriller", R.drawable.fnaf),
//            Item2("Five Night at Cio's", "Horror, Thriller", R.drawable.fnaf),
//            Item2("Devil May Cry", "Action, Sci-Fi", R.drawable.dmc),
//            Item2("Five Night at Cio's", "Horror, Thriller", R.drawable.fnaf),
//            Item2("Devil May Cry", "Action, Sci-Fi", R.drawable.dmc),
//            Item2("Heil Fuhrer", "Historical, Action", R.drawable.img),
//            Item2("Burning Hall", "Action, Thriller", R.drawable.pp2),
//            Item2("Bladerunner 2069", "Sci-Fi, Action", R.drawable.real),
//            Item2("Five Night at Cio's", "Horror, Thriller", R.drawable.fnaf),
//            Item2("Five Night at Cio's", "Horror, Thriller", R.drawable.fnaf),
//            Item2("Devil May Cry", "Action, Sci-Fi", R.drawable.dmc)
//        )
//
//        // set recycleview
//        recycleview2.layoutManager = GridLayoutManager(requireContext(),3)
//        recycleview2.adapter = ItemAdapter2(movieList.re)
//        setRecyclerViewHeightForGrid(recycleview2, 3)
//    }

    fun setRecyclerViewHeightForGrid(recyclerView: RecyclerView, spanCount: Int) {
        val adapter = recyclerView.adapter ?: return
        val itemCount = adapter.itemCount

        if (itemCount == 0) return

        recyclerView.post {
            val holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(0))
            adapter.onBindViewHolder(holder, 0)

            holder.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(recyclerView.width / spanCount, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.UNSPECIFIED
            )

            val itemHeight = holder.itemView.measuredHeight

            // Calculate total rows
            val rows = (itemCount + spanCount - 1) / spanCount

            val extraSpace = itemHeight

            // Set RecyclerView height
            val params = recyclerView.layoutParams
            params.height = (rows * itemHeight) + extraSpace // Add extra space for last row
            recyclerView.layoutParams = params
            recyclerView.requestLayout()
        }
    }
