package com.zs.my_ecommerce.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zs.my_ecommerce.api.Services
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.bean.Favorite
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.repository.ApiRepository
import com.zs.my_ecommerce.repository.DataBaseRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(val dataBase: MyDataBase) : ViewModel() {
    private val apiRepo = ApiRepository(Services.create())
    private val dbRepo = DataBaseRepository(dataBase)

    private var _products = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>> = _products

    private var _favorites = MutableLiveData<Map<Int, Favorite>>()
    val favorite get() = _favorites


    private var searchJob: Job? = null
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
            getFavorites()
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
            getFavorites()
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            val list = dbRepo.getAllFavorite()
            // 正确转换List为Map
            _favorites.value = list.associateBy { it.id }
        }
    }

    fun search(query: String) {
        // 取消前一个未完成的请求
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            try {
                delay(200)
                _products.value = apiRepo.searchProducts(query)
            } catch (e: Exception) {

            } finally {
                searchJob?.cancel()
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