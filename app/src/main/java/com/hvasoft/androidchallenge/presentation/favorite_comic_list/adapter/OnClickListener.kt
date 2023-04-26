package com.hvasoft.androidchallenge.presentation.favorite_comic_list.adapter

import com.hvasoft.androidchallenge.data.models.Comic

interface OnClickListener {
    fun onClick(comic: Comic)
}