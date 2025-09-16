package com.zs.my_ecommerce.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zs.my_ecommerce.api.Services
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.Favorite
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.repository.ApiRepository
import com.zs.my_ecommerce.repository.DataBaseRepository
import kotlinx.coroutines.launch

class HomeViewModel(val dataBase: MyDataBase) : ViewModel() {
    private val apiRepo = ApiRepository(Services.create())
    private val dbRepo = DataBaseRepository(dataBase)

    private var _products = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>> = _products

    private var _favorites = mutableMapOf<Int, Favorite>()
    val favorite get() = _favorites


    fun getProducts() {
        viewModelScope.launch {
            _products.value = apiRepo.getProducts()
        }
    }

    fun addFavorite(product: Product) {
        viewModelScope.launch {
            val favorite = Favorite(
                product.id,
                product.title,
                product.description,
                product.price,
                product.rating,
                product.thumbnail
            )
            dbRepo.insertFavorite(favorite)
            _favorites.put(favorite.id, favorite)
        }
    }

    fun removeFavorite(product: Product) {
        viewModelScope.launch {
            val favorite = Favorite(
                product.id,
                product.title,
                product.description,
                product.price,
                product.rating,
                product.thumbnail
            )
            dbRepo.deleteFavorite(favorite)
            _favorites.remove(favorite.id)
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            _favorites.apply {
                dbRepo.getAllFavorite().forEach { item ->
                    {
                        put(
                            item.id, item
                        )
                    }
                }
            }
        }
    }
}

class HomeViewModelFactory(private val dataBase: MyDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dataBase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}