package com.hvasoft.androidchallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hvasoft.androidchallenge.core.Constants
import com.hvasoft.androidchallenge.data.model.Thumbnail

@Entity(tableName = Constants.TABLE_NAME_CREATORS)
data class CreatorEntity(
    @PrimaryKey val id: Int,
    @SerializedName("fullName") val name: String,
    val resourceURI: String,
    val thumbnail: Thumbnail
)