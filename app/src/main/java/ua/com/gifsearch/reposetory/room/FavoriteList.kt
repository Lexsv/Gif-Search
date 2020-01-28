package ua.com.gifsearch.reposetory.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite")
data class FavoriteList (
    @PrimaryKey
    var id: Int,
    var json: String
    )

