package com.zs.my_ecommerce.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.zs.my_ecommerce.adapt.ImageAdapter
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private var productId: Int = 0
    private val pDetailVm: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(MyDataBase.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        binding.vm = pDetailVm
        binding.lifecycleOwner = this
        setContentView(binding.root)

        onClick()
        observe()

        val intent = getIntent()
        productId = intent.getIntExtra("id", 0)
        loadData(productId)

    }

    private fun onClick() {
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.favoriteCheckBox.setOnClickListener {
            pDetailVm.favoriteOperator()
        }
        binding.cartCheckBox.setOnClickListener {
            pDetailVm.cartOperator()
        }
        binding.buttonAddToCart.setOnClickListener {
            pDetailVm.addToCart()
        }
    }

    private fun loadData(id: Int) {
        pDetailVm.getProductDetail(id)
        pDetailVm.getCarts()
        pDetailVm.getFavorites()
    }

    private fun observe() {
        pDetailVm.product.observe(this) {
            binding.viewPager.adapter = ImageAdapter(it.images)
            binding.dotsIndicator.attachTo(binding.viewPager)
        }
        pDetailVm.favorites.observe(this) {
            Log.i("observe1", it.contains(productId).toString())
            binding.favoriteCheckBox.isChecked = it.contains(productId)
        }
        pDetailVm.carts.observe(this) {
            Log.i("observe2", it.contains(productId).toString())
            binding.cartCheckBox.isChecked = it.contains(productId)
        }
    }
}