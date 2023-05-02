package com.hvasoft.androidchallenge.presentation.home_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hvasoft.androidchallenge.R
import com.hvasoft.androidchallenge.core.utils.hideKeyboard
import com.hvasoft.androidchallenge.core.utils.showMessage
import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.databinding.FragmentHomeComicListBinding
import com.hvasoft.androidchallenge.domain.utils.Resource
import com.hvasoft.androidchallenge.presentation.home_list.adapter.HomeComicListAdapter
import com.hvasoft.androidchallenge.presentation.home_list.adapter.OnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeComicListFragment : Fragment(), OnClickListener {

    private var _binding: FragmentHomeComicListBinding? = null
    private val binding get() = _binding!!

    private val homeComicListViewModel: HomeComicListViewModel by viewModels()
    private lateinit var homeComicListAdapter: HomeComicListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeComicListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBtnFavorites()
        setupSearchView()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupBtnFavorites() {
        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_homeComicListFragment_to_favoriteComicListFragment)
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    homeComicListViewModel.getComicsByStartingTitle(it.lowercase())
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    homeComicListViewModel.getSavedComics()
                    binding.searchView.clearFocus()
                    hideKeyboard()
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        homeComicListAdapter = HomeComicListAdapter(this)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.main_columns))
            adapter = homeComicListAdapter
        }
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeComicListViewModel.stateFlowComicList.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.emptyStateLayout.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            showMessage(it.error?.localizedMessage.toString(), isError = true)
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        is Resource.Success -> {
                            homeComicListAdapter.submitList(it.data)
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.emptyStateLayout.run {
                                visibility = if (it.data.isNullOrEmpty()) {
                                    View.VISIBLE
                                } else {
                                    View.GONE
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * OnClickListener
     */
    override fun onClick(comic: Comic) {
        val action =
            HomeComicListFragmentDirections.actionHomeComicListFragmentToDetailComicFragment(comic.id.toString())
        findNavController().navigate(action)
    }

}