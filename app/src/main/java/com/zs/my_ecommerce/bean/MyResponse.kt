package com.zs.my_ecommerce.bean

data class MyResponse(
    val products: List<Product>,
    val limit: Int,
    val skip: Int,
    val total: Int? = null
)

data class Product(
    val id: Int,//named as productId, productName... and productList --> products
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>? = null
)