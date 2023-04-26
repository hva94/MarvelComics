package com.hvasoft.androidchallenge.data.local_db.dao

import androidx.room.*
import com.hvasoft.androidchallenge.data.models.Comic
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {

    @Query("SELECT * FROM comics")
    fun getAllComics() : Flow<List<Comic>>

    @Query("SELECT * FROM comics WHERE title LIKE :title")
    fun getComicsByStartingTitle(title: String) : Flow<List<Comic>>

    @Query("SELECT * FROM comics WHERE id = :id")
    fun getComic(id : String) : Flow<Comic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comics: List<Comic>)

    @Update
    suspend fun updateFavorite(comic: Comic): Int

}