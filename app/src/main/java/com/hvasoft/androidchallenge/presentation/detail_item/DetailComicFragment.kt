package com.hvasoft.androidchallenge.presentation.detail_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hvasoft.androidchallenge.R
import com.hvasoft.androidchallenge.core.exceptions.TypeError
import com.hvasoft.androidchallenge.core.utils.loadImage
import com.hvasoft.androidchallenge.core.utils.showMessage
import com.hvasoft.androidchallenge.core.utils.url
import com.hvasoft.androidchallenge.databinding.FragmentComicDetailBinding
import com.hvasoft.androidchallenge.domain.utils.Resource
import com.hvasoft.androidchallenge.presentation.detail_item.adapter.DetailVariantAdapter
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
        binding.btnFavorite.setOnClickListener {
            detailComicViewModel.updateFavorite()
        }
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        detailComicAdapter = DetailVariantAdapter()
        binding.rvVariants.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = detailComicAdapter
        }
    }

    private fun setupViewModel() {
        detailComicViewModel.getComicDetail(args.comicId)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.comicStateFlow.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            showMessage(resource.error?.localizedMessage.toString(), isError = true)
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        is Resource.Success -> {
                            val comic = resource.data!!
                            with(binding) {
                                ivComicPhoto.loadImage(comic.thumbnail.url())
                                pbComicImage.visibility = View.GONE
                                ivComicPhoto.scaleType = ImageView.ScaleType.CENTER_INSIDE
                                tvComicTitle.text = comic.title
                                tvComicResume.text = comic.description
                                val creator = comic.creators.items.first()
                                tvCreatorRole.text = getString(R.string.text_role, creator.role)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.creatorStateFlow.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            showMessage(it.error?.localizedMessage.toString(), isError = true)
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        is Resource.Success -> {
                            val creator = it.data!!
                            with(binding) {
                                progressBar.visibility = View.INVISIBLE
                                ivCreatorPhoto.loadImage(creator.thumbnail.url())
                                tvCreatorName.text = getString(R.string.text_creator, creator.name)
                                tvCreatorRole.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.variantListStateFlow.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            showMessage(it.error?.localizedMessage.toString(), isError = true)
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        is Resource.Success -> {
                            detailComicAdapter.submitList(it.data)
                            binding.progressBar.visibility = View.INVISIBLE
                            val variants = it.data!!
                            if (variants.isNotEmpty()) {
                                binding.tvVariants.visibility = View.VISIBLE
                                binding.rvVariants.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.isFavoriteTextStateFlow.collectLatest { isFavorite ->
                    binding.btnFavorite.text =
                        if (isFavorite) getString(R.string.text_remove_favorite)
                        else getString(R.string.text_add_favorite)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailComicViewModel.typeError.collectLatest { typeError ->
                    val msgRes = when (typeError) {
                        TypeError.UPDATE -> R.string.error_update
                        else -> null
                    }
                    msgRes?.let { showMessage(msgRes, isError = true) }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}