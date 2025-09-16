package com.zs.my_ecommerce.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zs.my_ecommerce.bean.Cart

@Dao
interface CartDao {
    @Insert
    suspend fun insertCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)

    @Query(value = "SELECT * FROM tableCard")
    suspend fun getAllCart(): List<Cart>

    @Query(value = "SELECT * FROM tableCard WHERE id = :id")
    suspend fun getSingleCart(id: Int): Cart
}