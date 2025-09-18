package com.zs.my_ecommerce.bean

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tableProduct")
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Float,
    val discountPercentage: Float,
    val rating: Float,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    @PrimaryKey(autoGenerate = true)
    val uuid: Int = 0
) {
    @Ignore
    @SerializedName("images")
    var images: List<String> = emptyList()
}