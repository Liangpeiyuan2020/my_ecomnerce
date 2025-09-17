package com.zs.my_ecommerce.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.my_ecommerce.api.Services
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.repository.ApiRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val apiRepo = ApiRepository(Services.create())

    private var _products = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>> = _products

    private var searchJob: Job? = null
    fun getProducts() {
        viewModelScope.launch {
            _products.value = apiRepo.getProducts()
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
