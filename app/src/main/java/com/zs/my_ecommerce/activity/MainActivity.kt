package com.zs.my_ecommerce.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.databinding.ActivityMainBinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.zs.my_ecommerce.fragment.cart.CartFragment
import com.zs.my_ecommerce.fragment.category.CategoryFragment
import com.zs.my_ecommerce.fragment.favorite.FavoriteFragment
import com.zs.my_ecommerce.fragment.home.HomeFragment
import com.zs.my_ecommerce.fragment.profile.ProfileFragment

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
        replaceFragment(HomeFragment.newInstance("param1", "param2"))
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.bottomNavigationView, fragment)
            commit()
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment.newInstance("param1", "param2"))
                }

                R.id.category -> {
                    replaceFragment(CategoryFragment())
                }

                R.id.favorite -> {
                    replaceFragment(FavoriteFragment())
                }

                R.id.cart -> {
                    replaceFragment(CartFragment())
                }

                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                }
            }
            true
        }
    }

}