package com.hvasoft.androidchallenge.presentation.favorite_comic_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hvasoft.androidchallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteComicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_comic, container, false)
    }

}