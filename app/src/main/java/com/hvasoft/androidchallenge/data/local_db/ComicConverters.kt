package com.hvasoft.androidchallenge.data.local_db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hvasoft.androidchallenge.data.models.*

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
    fun fromDatesList(dates: MutableList<Date>): String {
        return Gson().toJson(dates)
    }

    @TypeConverter
    fun toDatesList(datesString: String): MutableList<Date> {
        val listType = object : TypeToken<MutableList<Date>>() {}.type
        return Gson().fromJson(datesString, listType)
    }

    @TypeConverter
    fun fromImagesList(images: MutableList<Image>): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImagesList(imagesString: String): MutableList<Image> {
        val listType = object : TypeToken<MutableList<Image>>() {}.type
        return Gson().fromJson(imagesString, listType)
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