package com.hvasoft.androidchallenge.data.local_db

import android.content.Context
import androidx.room.*
import com.hvasoft.androidchallenge.core.Constants.DATABASE_NAME
import com.hvasoft.androidchallenge.data.local_db.dao.ComicDao
import com.hvasoft.androidchallenge.data.models.Comic

@Database(entities = [Comic::class], version = 1, exportSchema = false)
@TypeConverters(ComicConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getComicDao(): ComicDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(appContext: Context): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}