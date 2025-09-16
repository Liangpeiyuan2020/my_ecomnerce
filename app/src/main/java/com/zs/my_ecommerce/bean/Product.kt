package com.zs.my_ecommerce.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableProduct")
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    @PrimaryKey(autoGenerate = true)
    val uuid: Int = 0
)