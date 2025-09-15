package com.zs.my_ecommerce.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.databinding.ActivityMainBinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ViewDataBinding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {

    }
}