package com.example.androidavanzado

import MovieAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidavanzado.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private val movieList = mutableListOf<Movie>()
    private val genreMap = mapOf(
        "Animación" to "animation",
        "Acción/Aventura" to "action-adventure",
        "Clásicas" to "classic",
        "Comedia" to "comedy",
        "Drama" to "drama",
        "Horror" to "horror",
        "Misterio" to "mystery",
        "Ciencia Ficción" to "scifi-fantasy",
        "Western" to "western"
    )



    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //this.searchByGenre("animation")
        this.initRecyclerView()
        this.setupSpinner()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupSpinner() {
        val genres = resources.getStringArray(R.array.movie_genres)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.desplegable.adapter = adapter

        // Listener para detectar selección de género
        binding.desplegable.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedGenre = genres[position]  // Género en español
                val genreInEnglish = genreMap[selectedGenre]  // Traducción a inglés

                if (genreInEnglish != null) {
                    searchByGenre(genreInEnglish)
                } else {
                    Log.e("Error", "Género no encontrado en el mapa")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacemos nada
            }
        }
    }


    private fun initRecyclerView() {
        movieAdapter = MovieAdapter(movieList)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = movieAdapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.sampleapis.com/movies/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByGenre(genre: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<Movie>> = getRetrofit().create(MovieApiService::class.java).getMoviesByGenre(genre)
            val movies: List<Movie>? = call.body()

            runOnUiThread {
                if (call.isSuccessful && movies != null) {
                    movieList.clear()
                    movieList.addAll(movies) // Agregamos toda la lista correctamente
                    movieAdapter.notifyDataSetChanged() // Notificamos al RecyclerView
                } else {
                    Log.e("MainActivity", "Error en la peticion")
                    showError()
                }
            }
        }
    }


    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error en la peeticion", Toast.LENGTH_SHORT).show()
    }


}