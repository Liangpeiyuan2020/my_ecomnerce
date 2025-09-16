package com.zs.my_ecommerce.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zs.my_ecommerce.bean.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: Product)

    @Query(value = "DELETE  From tableProduct")
    suspend fun deleteAllProduct()

    @Query(value = "SELECT * FROM tableProduct")
    suspend fun getAllProduct(): List<Product>

    @Query(value = "SELECT * FROM tableProduct WHERE id = :id")
    suspend fun getSingleProduct(id: Int): Product
}