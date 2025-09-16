package com.zs.my_ecommerce.fragment.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zs.my_ecommerce.api.Services
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.fragment.home.HomeViewModel
import com.zs.my_ecommerce.repository.ApiRepository
import com.zs.my_ecommerce.repository.DataBaseRepository
import kotlinx.coroutines.launch

class CategoryViewModel(val dataBase: MyDataBase) : ViewModel() {
    private val apiRepo = ApiRepository(Services.create())
    private val dbRepo = DataBaseRepository(dataBase)

    private var _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private var _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = apiRepo.getCategories()

        }
    }

    fun getCategorizedProduct(category: String) {
        viewModelScope.launch {
            _products.value = apiRepo.getCategorizedProduct(category)
        }
    }
}

class CategoryViewModelFactory(val dataBase: MyDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(dataBase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}