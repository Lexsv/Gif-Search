package ua.com.gifsearch.reposetory.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoriteList::class), version = 2, exportSchema = false)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun getFavorite(): FavoriteDao

    companion object {
        @Volatile
        private var INIT: FavoriteDB? = null

        fun getInit(context: Context): FavoriteDB {
            synchronized(this) {
                var temp = INIT
                if (temp == null) {
                    temp = Room.databaseBuilder(context, FavoriteDB::class.java, "favorite")
                        .fallbackToDestructiveMigration()
                        .build()
                    INIT = temp
                }
                return temp
            }
        }
    }
}