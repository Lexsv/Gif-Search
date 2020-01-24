package ua.com.gifsearch.reposetory.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorit")
data class FavoritList (
    @PrimaryKey()
    var id: Int,
    var json: String
    )

