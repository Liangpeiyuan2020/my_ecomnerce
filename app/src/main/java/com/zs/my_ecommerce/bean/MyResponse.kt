package com.zs.my_ecommerce.bean

data class MyResponse(
    val products: List<Product>,
    val limit: Int,
    val skip: Int,
    val total: Int? = null
)

