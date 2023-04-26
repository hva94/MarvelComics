package com.hvasoft.androidchallenge.presentation.favorite_comic_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hvasoft.androidchallenge.R
import com.hvasoft.androidchallenge.core.utils.ExtFunc.showMessage
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.databinding.FragmentFavoriteComicListBinding
import com.hvasoft.androidchallenge.domain.utils.Status
import com.hvasoft.androidchallenge.presentation.favorite_comic_list.adapter.FavoriteComicListAdapter
import com.hvasoft.androidchallenge.presentation.favorite_comic_list.adapter.OnClickListener
import com.hvasoft.androidchallenge.presentation.utils.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteComicListFragment : Fragment(), OnClickListener {

    private var _binding: FragmentFavoriteComicListBinding? = null
    private val binding get() = _binding!!

    private val favoriteComicListViewModel: FavoriteComicListViewModel by viewModels()
    private lateinit var favoriteComicListAdapter: FavoriteComicListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteComicListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteComicListViewModel.stateFlowComicList.collectLatest {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Status.ERROR -> {
                            showMessage(it.message.toString(), isError = true)
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        Status.SUCCESS -> {
                            favoriteComicListAdapter.submitList(it.data)
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        favoriteComicListAdapter = FavoriteComicListAdapter(this)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.main_columns))
            addItemDecoration(GridSpacingItemDecoration())
            adapter = favoriteComicListAdapter
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
            FavoriteComicListFragmentDirections.actionFavoriteComicListFragmentToDetailComicFragment(comic.id.toString())
        findNavController().navigate(action)
    }

}