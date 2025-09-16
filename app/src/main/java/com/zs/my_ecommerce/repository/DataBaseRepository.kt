package com.zs.my_ecommerce.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.Cart
import com.zs.my_ecommerce.dataBase.Favorite
import com.zs.my_ecommerce.dataBase.MyDataBase

class DataBaseRepository(dataBase: MyDataBase) {
    val productDao = dataBase.getProductDao()
    val favoriteDao = dataBase.getFavorite()
    val cartDao = dataBase.getCartDao()

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun deleteAllProduct() {
        productDao.deleteAllProduct()
    }

    suspend fun getAllProduct(): List<Product> {
        return productDao.getAllProduct()
    }

    suspend fun getSingleProduct(id: Int): Product {
        return productDao.getSingleProduct(id)
    }

    suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite)
    }

    suspend fun getAllFavorite(): List<Favorite> {
        return favoriteDao.getAllFavorite()
    }

    suspend fun getSingleFavorite(id: Int): Favorite {
        return favoriteDao.getSingleFavorite(id)
    }

    suspend fun insertCart(cart: Cart) {
        cartDao.insertCart(cart)
    }

    suspend fun deleteCart(cart: Cart) {
        cartDao.deleteCart(cart)
    }

    suspend fun getAllCart(): List<Cart> {
        return cartDao.getAllCart()
    }

    suspend fun getSingleCart(id: Int): Cart {
        return cartDao.getSingleCart(id)
    }
}