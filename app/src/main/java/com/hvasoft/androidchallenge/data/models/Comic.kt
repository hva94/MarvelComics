package com.hvasoft.androidchallenge.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hvasoft.androidchallenge.core.Constants.TABLE_NAME_COMICS

@Entity(tableName = TABLE_NAME_COMICS)
data class Comic(
    val creators: Creators,
    var description: String?,
    val digitalId: Int,
    val ean: String,
    val format: String,
    @PrimaryKey
    val id: Int,
    val isbn: String,
    val issn: String,
    val issueNumber: Int,
    val modified: String,
    val pageCount: Int,
    val resourceURI: String,
    val thumbnail: Thumbnail,
    val title: String,
    val upc: String,
    val variantDescription: String,
    var variants: List<Variant>,
    var isFavorite: Boolean = false,
)