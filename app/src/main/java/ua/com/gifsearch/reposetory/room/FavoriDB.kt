package ua.com.gifsearch.reposetory.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoritList::class), version = 1, exportSchema = false)
abstract class FavoriDB : RoomDatabase() {
    abstract fun getFavorit(): FavoritDao

    companion object {
        @Volatile
        private var INIT: FavoriDB? = null

        fun getInit(context: Context): FavoriDB {
            synchronized(this) {
                var temp = INIT
                if (temp == null) {
                    temp = Room.databaseBuilder(context, FavoriDB::class.java, "favorit")
                        .fallbackToDestructiveMigration()
                        .build()
                    INIT = temp
                }
                return temp
            }
        }
    }
}