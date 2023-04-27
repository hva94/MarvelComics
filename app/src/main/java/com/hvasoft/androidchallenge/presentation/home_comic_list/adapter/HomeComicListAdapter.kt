package com.hvasoft.androidchallenge.presentation.home_comic_list.adapter

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
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.databinding.ItemComicBinding

class HomeComicListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Comic, RecyclerView.ViewHolder>(ComicDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_comic, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comic = getItem(position)

        with(holder as ViewHolder) {
            setListener(comic)

            binding.tvName.text = comic.title
            binding.ivComicPhoto.loadImage(comic.thumbnail.url())
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemComicBinding.bind(view)

        fun setListener(comic: Comic) {
            binding.root.setOnClickListener {
                onClickListener.onClick(comic)
            }
        }
    }

    class ComicDiffCallback : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem == newItem
        }
    }

}