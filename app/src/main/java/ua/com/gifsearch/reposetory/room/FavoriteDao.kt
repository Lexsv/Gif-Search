package ua.com.gifsearch.reposetory.room

import androidx.room.*


@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<FavoriteList>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getById(id: Int): FavoriteList

    @Query("DELETE FROM favorite")
    fun deleteAll()

    @Insert
    fun insert(favoriteList: FavoriteList)

    @Update
    fun update(favoriteList: FavoriteList)

    @Delete
    fun delete(favoriteList: FavoriteList)
}