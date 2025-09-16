package com.zs.my_ecommerce.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableProduct")
data class TableProduct(
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
    val images: List<String>? = null,
    @PrimaryKey(autoGenerate = true)
    val uuid: Int
)