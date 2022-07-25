package com.sample.project.watchbox.ui.movies.moviedetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.project.watchbox.data.model.Failure
import com.sample.project.watchbox.data.model.Loading
import com.sample.project.watchbox.data.model.Success
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.databinding.FragmentMovieDetailsBinding
import com.sample.project.watchbox.ui.base.BaseFragment
import com.sample.project.watchbox.ui.base.Inflate
import com.sample.project.watchbox.ui.movies.MoviesViewModel
import com.sample.project.watchbox.utils.Constants.DEEPLINK_BODY
import com.sample.project.watchbox.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    override val inflate: Inflate<FragmentMovieDetailsBinding> = FragmentMovieDetailsBinding::inflate
    private val viewModel by viewModels<MoviesViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        viewModel.getMovieDetails(movieId)
        initViews(movieId)
        initObservers(movieId)
    }

    private fun initViews(movieId: String) {
        binding.imageViewUnliked.setOnClickListener {
            viewModel.movie.value.detailedMovie?.let { detailedMovie ->
                viewModel.addToFavorites(args.movieId, detailedMovie)
            }
        }
        binding.imageViewLiked.setOnClickListener {
            viewModel.removeFromFavorites(args.movieId)
        }
        binding.imageViewShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, DEEPLINK_BODY + movieId)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun initObservers(movieId: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.movie.collect { detailedMovieState ->
                        binding.progressBar.show(detailedMovieState.isLoading)
                        val detailedMovie = detailedMovieState.detailedMovie
                        when {
                            detailedMovie != null -> {
                                updateView(detailedMovie)
                                launch {
                                    viewModel.isAddedToFavorite(movieId).collect { isFavorite ->
                                        binding.imageViewShare.show(true)
                                        if (isFavorite) {
                                            binding.imageViewUnliked.show(false)
                                            binding.imageViewLiked.show(true)
                                        } else {
                                            binding.imageViewLiked.show(false)
                                            binding.imageViewUnliked.show(true)
                                        }
                                    }
                                }
                            }
                            detailedMovieState.error != null -> {
                                Toast.makeText(requireContext(), detailedMovieState.error, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateView(movieItem: DetailedMovie) {
        Glide.with(binding.moviePoster).load(movieItem.posterImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.moviePoster)
        binding.textViewTitle.text = movieItem.title
        binding.textViewShortInfo.text = String.format(
            "%s.\n%s.\n%s",
            movieItem.genre,
            movieItem.runtime,
            movieItem.released
        )
        binding.textViewActors.text = movieItem.actors
        binding.textViewSummary.text = String.format(
            "imdb: %s (votes: %s) %s",
            movieItem.imdbRating,
            movieItem.imdbVotes,
            movieItem.boxOffice
        )
    }
}
