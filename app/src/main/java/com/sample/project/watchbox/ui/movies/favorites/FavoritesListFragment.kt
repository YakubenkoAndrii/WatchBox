package com.sample.project.watchbox.ui.movies.favorites

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sample.project.watchbox.R
import com.sample.project.watchbox.data.model.*
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.databinding.FragmentMoviesFavoritesBinding
import com.sample.project.watchbox.ui.base.BaseFragment
import com.sample.project.watchbox.ui.base.Inflate
import com.sample.project.watchbox.ui.movies.MoviesViewModel
import com.sample.project.watchbox.utils.BindingViewHolder
import com.sample.project.watchbox.utils.RecyclerViewHelper
import com.sample.project.watchbox.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesListFragment : BaseFragment<FragmentMoviesFavoritesBinding>() {

    override val inflate: Inflate<FragmentMoviesFavoritesBinding> = FragmentMoviesFavoritesBinding::inflate
    private val viewModel by viewModels<MoviesViewModel>()

    private var moviesListRv: RecyclerViewHelper<BindingViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        initOptionsMenu()
        viewModel.getMovies()
    }

    private fun initRecyclerView() {
        moviesListRv = RecyclerViewHelper(
            binding.recyclerViewMovies,
            FavoritesListAdapter()
        )
    }

    private fun initObservers() {
        // movies list for recyclerView
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.localMovies.collect { dataWrapper ->
                    when (dataWrapper) {
                        is Success -> {
                            binding.textViewEmptyFavorites.show(false)
                            updateRecyclerView(dataWrapper.data)
                        }
                        is EmptySuccess -> {
                            binding.textViewEmptyFavorites.show(true)
                            updateRecyclerView(listOf())
                        }
                        is Failure -> {
                            Toast.makeText(requireContext(), dataWrapper.httpError.message, Toast.LENGTH_LONG).show()
                        }
                        is Loading -> {
                            binding.progressBar.show(dataWrapper.loading)
                        }
                    }
                }
            }
        }
    }

    private fun updateRecyclerView(movies: List<DetailedMovie>) {
        (moviesListRv?.adapter as FavoritesListAdapter).moviesList = movies
    }

    private fun initOptionsMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_delete_favorites, menu)
                initDeleteAll(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initDeleteAll(menu: Menu) {
        val deleteAll = menu.findItem(R.id.action_delete_all)
        deleteAll.setOnMenuItemClickListener {
            showDialog()
            return@setOnMenuItemClickListener true
        }
    }

    private fun showDialog() {
        activity?.let {
            AlertDialog.Builder(it, R.style.MaterialThemeDialog)
                .setTitle(R.string.dialog_remove_all)
                .setPositiveButton(R.string.action_ok) { _, _ -> viewModel.deleteAllFavorites() }
                .setNegativeButton(R.string.action_cancel) { _, _ -> }
                .create()
                .show()
        }
    }
}
