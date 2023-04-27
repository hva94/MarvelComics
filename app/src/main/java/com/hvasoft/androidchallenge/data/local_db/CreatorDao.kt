package com.hvasoft.androidchallenge.data.local_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hvasoft.androidchallenge.data.models.Creator
import kotlinx.coroutines.flow.Flow

@Dao
interface CreatorDao {

    @Query("SELECT * FROM creators WHERE id = :id")
    fun getCreator(id : String) : Flow<Creator>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreator(creator: Creator)

}