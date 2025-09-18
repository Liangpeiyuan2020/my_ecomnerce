package com.zs.my_ecommerce.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zs.my_ecommerce.api.Services
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.bean.Favorite
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.repository.ApiRepository
import com.zs.my_ecommerce.repository.DataBaseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductDetailViewModel(val dataBase: MyDataBase) : ViewModel() {
    private val apiRepo = ApiRepository(Services.create())
    private val dbRepo = DataBaseRepository(dataBase)

    private var _favorites = MutableLiveData<Map<Int, Favorite>>(emptyMap())
    val favorites: LiveData<Map<Int, Favorite>> = _favorites

    private var _carts = MutableLiveData<Map<Int, Cart>>()
    val carts: LiveData<Map<Int, Cart>> = _carts

    private var _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun getProductDetail(id: Int) {
        viewModelScope.launch {
            _product.value = apiRepo.getProductDetail(id)
        }
    }

    fun favoriteOperator() {
        val favorite = Favorite(
            _product.value!!.id,
            _product.value!!.title,
            _product.value!!.description,
            _product.value!!.price,
            _product.value!!.rating,
            _product.value!!.thumbnail
        )
        viewModelScope.launch {
            if (_favorites.value!!.contains(favorite.id)) {
                Log.i("observe3", "")
                dbRepo.deleteFavorite(favorite)
            } else {
                Log.i("observe4", "")
                dbRepo.insertFavorite(favorite)
            }
            getFavorites()
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            val list = dbRepo.getAllFavorite()
            _favorites.value = list.associateBy { it.id }
            Log.i("observe3", _favorites.value?.get(_product.value?.id ?: 0).toString())
        }
    }


    fun getCarts() {
        viewModelScope.launch {
            _carts.value = dbRepo.getAllCart().associateBy { it.id }
        }
    }

    fun cartOperator() {
        val cart = Cart(
            _product.value!!.id,
            _product.value!!.title,
            _product.value!!.description,
            _product.value!!.price,
            _product.value!!.rating,
            _product.value!!.thumbnail
        )
        viewModelScope.launch {
            if (_carts.value!!.contains(cart.id)) {
                dbRepo.deleteCart(cart)
            } else {
                dbRepo.insertCart(cart)
            }
            getCarts()
        }
    }

    fun addToCart() {
        val cart = Cart(
            _product.value!!.id,
            _product.value!!.title,
            _product.value!!.description,
            _product.value!!.price,
            _product.value!!.rating,
            _product.value!!.thumbnail
        )
        viewModelScope.launch {
            delay(100)
            if (_carts.value!!.contains(cart.id)) {
                dbRepo.deleteCart(cart)
                cart.num = _carts.value!!.get(cart.id)!!.num + 1
                dbRepo.insertCart(cart)
            } else {
                cart.num = 1
                dbRepo.insertCart(cart)
            }
            getCarts()
        }
    }

}

class ProductDetailViewModelFactory(val dataBase: MyDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(dataBase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}