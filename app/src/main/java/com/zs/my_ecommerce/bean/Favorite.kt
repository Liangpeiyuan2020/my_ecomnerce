package com.zs.my_ecommerce.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableFavorite")
data class Favorite(
    val id: Int,
    val title: String,
    val description: String,
    val price: Float,
    val rating: Float,
    val thumbnail: String,
    @PrimaryKey(autoGenerate = true)
    val uuid: Int = 0
)