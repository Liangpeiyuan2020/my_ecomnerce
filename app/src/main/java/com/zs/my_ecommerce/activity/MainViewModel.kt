package com.zs.my_ecommerce.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.bean.Favorite
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.repository.DataBaseRepository
import kotlinx.coroutines.launch

class MainViewModel(val dataBase: MyDataBase) : ViewModel() {
    private val dbRepo = DataBaseRepository(dataBase)

    private var _favorites = MutableLiveData<Map<Int, Favorite>>(emptyMap())
    val favorites: LiveData<Map<Int, Favorite>> = _favorites

    private var _carts = MutableLiveData<List<Cart>>()
    val carts: LiveData<List<Cart>> = _carts


    fun favoriteOperator(product: Product) {
        val favorite = Favorite(
            product.id,
            product.title,
            product.description,
            product.price,
            product.rating,
            product.thumbnail
        )
        viewModelScope.launch {
            if (_favorites.value!!.contains(favorite.id)) {
                dbRepo.deleteFavorite(favorite)
            } else {
                dbRepo.insertFavorite(favorite)
            }
            getFavorites()
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            val list = dbRepo.getAllFavorite()
            _favorites.value = list.associateBy { it.id }
        }
    }

    fun getCarts() {
        viewModelScope.launch {
            _carts.value = dbRepo.getAllCart()
        }
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            dbRepo.insertCart(cart)
        }
    }

    fun deleteFromCart(cart: Cart) {
        viewModelScope.launch {

        }
    }

    fun minusFromCart(cart: Cart) {
        viewModelScope.launch {

        }
    }
}

class MainViewModelFactory(val dataBase: MyDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dataBase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}