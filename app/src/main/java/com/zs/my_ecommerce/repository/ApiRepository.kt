package com.zs.my_ecommerce.repository

import com.zs.my_ecommerce.api.Services
import com.zs.my_ecommerce.bean.MyResponse
import com.zs.my_ecommerce.bean.Product


class Repository(val services: Services) {
    suspend fun getProducts(): List<Product> {
        return services.getProducts().products
    }

    suspend fun searchProducts(query: String): List<Product> {
        return services.searchProduct(query).products
    }

    suspend fun getCategories(): List<String> {
        return services.getCategories()
    }

    suspend fun getCategorizedProduct(category: String): List<Product> {
        return services.getCategorizedProduct(category).products
    }
}