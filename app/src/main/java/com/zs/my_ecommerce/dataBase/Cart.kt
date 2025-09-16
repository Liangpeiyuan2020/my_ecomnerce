package com.zs.my_ecommerce.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableCard")
data class Cart(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String,
    val num: Int,
    @PrimaryKey(autoGenerate = true)
    val uuid: Int = 0
)