package com.zs.my_ecommerce.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableCard")
data class Cart(
    val id: Int,
    val title: String,
    val description: String,
    val price: Float,
    val rating: Float,
    val thumbnail: String,
    var num: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val uuid: Int = 0
)