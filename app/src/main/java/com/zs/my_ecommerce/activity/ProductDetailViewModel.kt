package com.zs.my_ecommerce.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_ecommerce.api.Services
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.repository.ApiRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {
    private val apiRepo = ApiRepository(Services.create())

    private var _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun getProductDetail(id: Int) {
        viewModelScope.launch {
            _product.value = apiRepo.getProductDetail(id)
        }
    }
}