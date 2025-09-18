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
    private val pDetailVm: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(MyDataBase.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
//        binding.product = pDetailVm.product.value
        binding.vm = pDetailVm
        binding.lifecycleOwner = this
        setContentView(binding.root)

        onClick()
        observe()

        val intent = getIntent()
        val id = intent.getIntExtra("id", 0)
        loadData(id)

        binding.favoriteCheckBox.isChecked = true
    }

    private fun onClick() {
        binding.backButton.setOnClickListener {
            Log.i("observe10", "")
            finish()
        }
        binding.favoriteCheckBox.setOnClickListener {
            Log.i("observe11", "")
            pDetailVm.favoriteOperator()
        }
        binding.cartCheckBox.setOnClickListener {
            Log.i("observe12", "")
            pDetailVm.cartOperator()
        }
        binding.buttonAddToCart.setOnClickListener {
            Log.i("observe13", "")
            pDetailVm.addToCart()
        }
    }

    private fun loadData(id: Int) {
        pDetailVm.getProductDetail(id)
        pDetailVm.getCarts()
        pDetailVm.getFavorites()
    }

    private fun observe() {
//        pDetailVm.product.observe(this) {
//            binding.viewPager.adapter = ImageAdapter(it.images)
//            binding.dotsIndicator.attachTo(binding.viewPager)
//        }
        pDetailVm.favorites.observe(this) {
            Log.i("observe1", it.contains(pDetailVm.product.value?.id).toString())
            binding.favoriteCheckBox.isChecked = it.contains(pDetailVm.product.value?.id)
        }
        pDetailVm.carts.observe(this) {
            Log.i("observe2", it.contains(pDetailVm.product.value?.id).toString())
            binding.cartCheckBox.isChecked = it.contains(pDetailVm.product.value?.id)
        }
    }
}