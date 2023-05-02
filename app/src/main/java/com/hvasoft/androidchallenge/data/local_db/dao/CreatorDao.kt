package com.hvasoft.androidchallenge.data.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hvasoft.androidchallenge.data.model.CreatorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreatorDao {

    @Query("SELECT * FROM creators WHERE id = :id")
    fun getCreatorById(id : String) : Flow<CreatorEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreator(creator: CreatorEntity)

}