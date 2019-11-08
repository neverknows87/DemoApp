package com.desmond.demoapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.desmond.demoapp.model.Album
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Album::class], version = 1)
abstract class AlbumRoomDb : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile
        private var INSTANCE: AlbumRoomDb? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AlbumRoomDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlbumRoomDb::class.java,
                    "album_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}