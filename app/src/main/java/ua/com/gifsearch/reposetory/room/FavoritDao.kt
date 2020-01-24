package ua.com.gifsearch.reposetory.room

import androidx.room.*


@Dao
interface FavoritDao {
    @Query("SELECT * FROM favorit")
    fun getAll(): List<FavoritList>

    @Query("SELECT * FROM favorit WHERE id = :id")
    fun getById(id: Int): FavoritList

    @Query("DELETE FROM favorit")
    fun deleteAll()

    @Insert
    fun insert(favoritList: FavoritList)

    @Update
    fun update(favoritList: FavoritList)

    @Delete
    fun delete(favoritList: FavoritList)
}