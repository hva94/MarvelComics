package com.hvasoft.androidchallenge.data.local_db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hvasoft.androidchallenge.data.model.Creators
import com.hvasoft.androidchallenge.data.model.Thumbnail
import com.hvasoft.androidchallenge.data.model.Variant

class ComicConverters {
    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail?): String? {
        return Gson().toJson(thumbnail)
    }

    @TypeConverter
    fun toThumbnail(thumbnail: String?): Thumbnail? {
        return Gson().fromJson(thumbnail, Thumbnail::class.java)
    }

    @TypeConverter
    fun fromCreators(creators: Creators): String? {
        return Gson().toJson(creators)
    }

    @TypeConverter
    fun toCreators(creators: String): Creators? {
        return Gson().fromJson(creators, Creators::class.java)
    }

    @TypeConverter
    fun fromVariantList(variants: MutableList<Variant>): String {
        return Gson().toJson(variants)
    }

    @TypeConverter
    fun toVariantsList(variantsString: String): MutableList<Variant> {
        val listType = object : TypeToken<MutableList<Variant>>() {}.type
        return Gson().fromJson(variantsString, listType)
    }

}