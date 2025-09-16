package com.zs.my_ecommerce.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query(value = "SELECT * FROM tableFavorite")
    suspend fun getAllFavorite(): List<Favorite>

    @Query(value = "SELECT * FROM tableFavorite WHERE id = :id")
    suspend fun getSingleFavorite(id: Int): Favorite
}