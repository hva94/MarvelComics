package com.hvasoft.androidchallenge.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hvasoft.androidchallenge.R
import com.hvasoft.androidchallenge.core.exceptions.TypeError
import com.hvasoft.androidchallenge.core.utils.ExtFunc.loadImage
import com.hvasoft.androidchallenge.core.utils.ExtFunc.showMessage
import com.hvasoft.androidchallenge.core.utils.ExtFunc.url
import com.hvasoft.androidchallenge.databinding.FragmentComicDetailBinding
import com.hvasoft.androidchallenge.presentation.detail.adapter.DetailVariantAdapter
import com.hvasoft.androidchallenge.presentation.utils.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailComicFragment : Fragment() {

    private var _binding: FragmentComicDetailBinding? = null
    private val binding get() = _binding!!

    private val detailComicViewModel: DetailComicViewModel by viewModels()
    private lateinit var detailComicAdapter: DetailVariantAdapter
    private val args: DetailComicFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComicDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        detailComicAdapter = DetailVariantAdapter()
        binding.rvVariants.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(GridSpacingItemDecoration())
            adapter = detailComicAdapter
        }
    }

    private fun setupListener() {
        binding.btnFavorite.setOnClickListener {
            detailComicViewModel.updateFavorite()
        }
    }

    private fun setupViewModel() {
        detailComicViewModel.getComicDetail(args.comicId)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.displayComic.collectLatest { nullableComic ->
                    nullableComic?.let { comic ->
                        with(binding) {
                            ivComicPhoto.loadImage(comic.thumbnail.url())
                            tvComicTitle.text = comic.title
                            tvComicResume.text = comic.description
                            val creator = comic.creators.items.first()
                            ivCreatorPhoto.loadImage(creator.thumbnail?.url())
                            tvCreatorName.text = getString(R.string.text_creator, creator.name)
                            tvCreatorRole.text = getString(R.string.text_role, creator.role)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.stateFlowVariantList.collectLatest {
                    detailComicAdapter.submitList(it)
                    binding.tvVariants.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.isLoading.collectLatest { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.typeError.collectLatest { typeError ->
                    val msg = when (typeError) {
                        TypeError.NETWORK -> getString(R.string.error_network)
                        TypeError.UPDATE -> getString(R.string.error_update)
                        else -> null
                    }
                    msg?.let { showMessage(msg, isError = true) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.isFavoriteTextStateFlow.collectLatest { isFavorite ->
                    binding.btnFavorite.text = if (isFavorite) getString(R.string.text_remove_favorite)
                    else getString(R.string.text_add_favorite)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}