package com.hvasoft.androidchallenge.presentation.detail_item.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hvasoft.androidchallenge.R
import com.hvasoft.androidchallenge.core.utils.loadImage
import com.hvasoft.androidchallenge.core.utils.url
import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.databinding.ItemVariantBinding

class DetailVariantAdapter : ListAdapter<Comic, RecyclerView.ViewHolder>(VariantDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_variant, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comic = getItem(position)

        with(holder as ViewHolder) {
            binding.tvName.text = comic.title
            binding.ivVariant.loadImage(comic.thumbnail.url())
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemVariantBinding.bind(view)
    }

    class VariantDiffCallback : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem == newItem
        }
    }

}