package com.zs.my_ecommerce.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableFavorite")
data class Favorite(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String,
    @PrimaryKey(autoGenerate = true)
    val uuid: Int = 0
)