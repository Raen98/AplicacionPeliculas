package com.example.androidavanzado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.androidavanzado.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso


class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun bind(movie: Movie) {
        binding.movieTitle.text = movie.title
        Picasso.get().load(movie.poster).into(binding.moviePoster)
    }
}