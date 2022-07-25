package com.sample.project.watchbox.ui.movies.movieslist

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sample.project.watchbox.R
import com.sample.project.watchbox.data.model.*
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.databinding.FragmentMoviesListBinding
import com.sample.project.watchbox.ui.base.BaseFragment
import com.sample.project.watchbox.ui.base.Inflate
import com.sample.project.watchbox.ui.movies.MoviesViewModel
import com.sample.project.watchbox.ui.movies.SearchMovieState
import com.sample.project.watchbox.utils.BindingViewHolder
import com.sample.project.watchbox.utils.RecyclerViewHelper
import com.sample.project.watchbox.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesListFragment : BaseFragment<FragmentMoviesListBinding>() {

    override val inflate: Inflate<FragmentMoviesListBinding> = FragmentMoviesListBinding::inflate
    private val viewModel by viewModels<MoviesViewModel>()

    private var moviesListRv: RecyclerViewHelper<BindingViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        initOptionsMenu()
    }

    private fun checkDeepLinks(data: Uri?) {
        data?.let {
            val movieId = data.toString().split("/").last()
            toMovieDetailsFragment(movieId)
            activity?.intent?.data = null
        }
    }

    private fun initRecyclerView() {
        moviesListRv = RecyclerViewHelper(
            binding.recyclerViewMovies,
            MoviesListAdapter(
                itemClickListener = { movie ->
                    toMovieDetailsFragment(movie.imdbId)
                }
            )
        )
    }

    private fun toMovieDetailsFragment(movieId: String) {
        val bundle = bundleOf("movieId" to movieId)
        view?.findNavController()?.navigate(R.id.action_moviesListFragment_to_movieDetailsFragment, bundle)
    }

    private fun initObservers() {
        // movies list for recyclerView
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.movies.collect { searchMovieState ->
                    binding.progressBar.show(searchMovieState.isLoading)
                    when {
                        searchMovieState.moviesList != null -> {
                            val moviesIsEmpty = searchMovieState.moviesList.isEmpty()
                            binding.textViewEmpty.show(moviesIsEmpty)
                            binding.textViewWelcome.show(false)
                            updateRecyclerView(searchMovieState.moviesList)
                        }
                        searchMovieState.error != null -> {
                            Toast.makeText(requireContext(), searchMovieState.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun updateRecyclerView(movies: List<Movie>) {
        (moviesListRv?.adapter as MoviesListAdapter).moviesList = movies
    }

    private fun initOptionsMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_search, menu)
                menuInflater.inflate(R.menu.menu_favorites, menu)
                initSearchView(menu)
                initFavorites(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initFavorites(menu: Menu) {
        val favorites = menu.findItem(R.id.action_favorite)
        favorites.setOnMenuItemClickListener {
            view?.findNavController()?.navigate(R.id.action_moviesListFragment_to_favoritesListFragment, bundleOf())
            return@setOnMenuItemClickListener true
        }
    }

    private fun initSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(inputSource: String?): Boolean {
                inputSource?.let { query ->
                    viewModel.searchMovies(query)
                }
                return false
            }

            override fun onQueryTextChange(inputSource: String?): Boolean {
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        checkDeepLinks(activity?.intent?.data)
    }
}
