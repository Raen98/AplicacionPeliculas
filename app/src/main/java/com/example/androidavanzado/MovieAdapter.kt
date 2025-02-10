import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.androidavanzado.Movie
import com.example.androidavanzado.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(private val movieList: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.movieTitle.text = movie.title
        Picasso.get().load(movie.poster).into(holder.binding.moviePoster)

        //Abrir IMDB
        holder.binding.moviePoster.setOnClickListener {
            val imdbId = "https://www.imdb.com/title/${movie.imdbId}/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbId))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
