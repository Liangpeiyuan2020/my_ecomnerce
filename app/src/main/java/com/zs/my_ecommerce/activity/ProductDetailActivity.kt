package com.zs.my_ecommerce.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.zs.my_ecommerce.adapt.ImageAdapter
import com.zs.my_ecommerce.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val pDetailVm: ProductDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
        val intent = getIntent()
        val id = intent.getIntExtra("id", 0)
        loadData(id)
    }

    private fun loadData(id: Int) {
        pDetailVm.getProductDetail(id)
    }

    private fun observe() {
        pDetailVm.product.observe(this) {
            binding.viewPager.adapter = ImageAdapter(it.images)
            binding.dotsIndicator.attachTo(binding.viewPager)
        }
    }
}