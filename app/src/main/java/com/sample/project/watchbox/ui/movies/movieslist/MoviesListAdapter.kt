package com.sample.project.watchbox.ui.movies.movieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.databinding.ItemMovieBinding
import com.sample.project.watchbox.utils.BindingViewHolder

class MoviesListAdapter(
    val itemClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<BindingViewHolder>() {

    var moviesList = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = moviesList[position]
        val binding = (holder.binding as ItemMovieBinding)

        Glide.with(binding.moviePoster).load(item.posterImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.moviePoster)

        binding.textViewTitle.text = item.title
        binding.textViewYear.text = item.year
        binding.textViewType.text = item.type

        binding.root.setOnClickListener { itemClickListener.invoke(item) }
    }

    override fun getItemCount(): Int = moviesList.size
}
