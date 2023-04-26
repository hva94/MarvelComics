package com.hvasoft.androidchallenge.presentation.home_comic_list

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
import com.hvasoft.androidchallenge.core.utils.ExtFunc.hideKeyboard
import com.hvasoft.androidchallenge.core.utils.ExtFunc.showMessage
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.databinding.FragmentHomeComicListBinding
import com.hvasoft.androidchallenge.domain.utils.Status
import com.hvasoft.androidchallenge.presentation.home_comic_list.adapter.HomeComicListAdapter
import com.hvasoft.androidchallenge.presentation.home_comic_list.adapter.OnClickListener
import com.hvasoft.androidchallenge.presentation.utils.GridSpacingItemDecoration
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

        setupSearchView()
        setupRecyclerView()
        setupViewModel()
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
                    homeComicListViewModel.getComics()
                    hideKeyboard()
                    binding.searchView.clearFocus()
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
            addItemDecoration(GridSpacingItemDecoration())
            adapter = homeComicListAdapter
        }
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeComicListViewModel.stateFlowComicList.collectLatest {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Status.ERROR -> {
                            showMessage(it.message.toString(), isError = true)
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        Status.SUCCESS -> {
                            homeComicListAdapter.submitList(it.data)
                            binding.progressBar.visibility = View.INVISIBLE
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