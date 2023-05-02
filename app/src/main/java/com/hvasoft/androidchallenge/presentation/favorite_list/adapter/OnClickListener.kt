package com.hvasoft.androidchallenge.presentation.favorite_list.adapter

import com.hvasoft.androidchallenge.data.model.Comic

interface OnClickListener {
    fun onClick(comic: Comic)
}