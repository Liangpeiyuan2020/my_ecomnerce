package com.zs.my_ecommerce.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.databinding.ActivityMainBinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zs.my_ecommerce.common.AppGlobals
import com.zs.my_ecommerce.common.GlobalLoadingState
import com.zs.my_ecommerce.common.MyNavigationController
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.fragment.cart.CartFragment
import com.zs.my_ecommerce.fragment.category.CategoryFragment
import com.zs.my_ecommerce.fragment.favorite.FavoriteFragment
import com.zs.my_ecommerce.fragment.home.HomeFragment
import com.zs.my_ecommerce.fragment.profile.ProfileFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MyNavigationController {
    //    private lateinit var binding: ViewDataBinding
    private lateinit var binding: ActivityMainBinding
    private val mainVm: MainViewModel by viewModels {
        MainViewModelFactory(MyDataBase.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupLoadingOverlay()
        mainVm.getFavorites()
        mainVm.getCarts()
        initBottomNavigation()
        replaceFragment(HomeFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

    private fun setupLoadingOverlay() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                AppGlobals.loadingState.collect { state ->
                    when (state) {
                        GlobalLoadingState.LOADING -> binding.loadingTip.loading()
                        GlobalLoadingState.PAGING_LOADING_MORE -> binding.loadingTip.showEmpty()
                        else -> binding.loadingTip.dismiss()
                    }
                }
            }
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment.newInstance())
                }

                R.id.category -> {
                    replaceFragment(CategoryFragment.newInstance())
                }

                R.id.favorite -> {
                    replaceFragment(FavoriteFragment.newInstance())
                }

                R.id.cart -> {
                    replaceFragment(CartFragment.newInstance())
                }

                R.id.profile -> {
                    replaceFragment(ProfileFragment.newInstance())
                }
            }
            true
        }
    }

    // 实现接口方法
    override fun navigateToTab(itemId: Int) {
        binding.bottomNavigationView.selectedItemId = itemId
    }
}