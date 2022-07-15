package com.sample.project.watchbox.ui.movies.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.project.watchbox.R
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.databinding.ItemFavoriteMovieBinding
import com.sample.project.watchbox.utils.BindingViewHolder

class FavoritesListAdapter : RecyclerView.Adapter<BindingViewHolder>() {

    var moviesList = listOf<DetailedMovie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = moviesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = moviesList[position]
        val binding = (holder.binding as ItemFavoriteMovieBinding)

        Glide.with(binding.moviePoster).load(item.posterImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.moviePoster)

        binding.textViewTitle.text = item.title
        binding.textViewShortInfo.text = binding.root.context.getString(
            R.string.movie_short_info,
            item.genre,
            item.runtime,
            item.released
        )
        binding.textViewActors.text = item.actors
        binding.textViewSummary.text = binding.root.context.getString(
            R.string.movie_rating_money_info,
            item.imdbRating,
            item.imdbVotes,
            item.boxOffice
        )
    }
}
