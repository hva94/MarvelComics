package com.hvasoft.androidchallenge.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hvasoft.androidchallenge.core.Constants
import com.hvasoft.androidchallenge.core.Constants.TABLE_NAME_CREATORS

@Entity(tableName = TABLE_NAME_CREATORS)
data class Creator(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val resourceURI: String,
    val role: String,
    var thumbnail: Thumbnail? = null
)