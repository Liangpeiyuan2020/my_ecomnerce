package com.zs.my_ecommerce.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zs.my_ecommerce.utils.BaseApp

@Database(entities = [TableProduct::class, Favorite::class, Cart::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCartDao(): CartDao
    abstract fun getFavorite(): FavoriteDao

    companion object {
        private var instance: MyDataBase? = null
        private val lock = Any()
        fun getInstance(): MyDataBase {
            return instance ?: synchronized(lock) {
                instance ?: Room.databaseBuilder(
                    BaseApp.getContext(),
                    MyDataBase::class.java,
                    "myDataBase"
                ).build().also {
                    instance = it
                }
            }
        }
    }
}