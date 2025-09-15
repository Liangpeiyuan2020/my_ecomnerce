package com.zs.my_ecommerce.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_ecommerce.api.Services
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repo = Repository(Services.create())
    private var _products = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>> = _products

    fun getProducts() {
        viewModelScope.launch {
            _products.value = repo.getProducts()
        }
    }
}