package com.zs.my_ecommerce.fragment.favorite

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.activity.MainViewModel
import com.zs.my_ecommerce.activity.MainViewModelFactory
import com.zs.my_ecommerce.activity.ProductDetailActivity
import com.zs.my_ecommerce.adapt.FavoriteAdapter
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.bean.Favorite
import com.zs.my_ecommerce.common.observeOnce
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteVm: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val mainVm: MainViewModel by activityViewModels {
        MainViewModelFactory(MyDataBase.getInstance())
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        observe()
        mainVm.getFavorites()
    }

    private fun onClick() {
        // 处理RecyclerView的滑动冲突
        binding.recycleView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // 当手指按下时，关闭所有打开的滑动项
                        favoriteAdapter.closeAllItems(binding.recycleView)
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    private fun observe() {
        mainVm.favorites.observeOnce(viewLifecycleOwner) {
            val favoriteList = it.values.toList()
            binding.recycleView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                favoriteAdapter = FavoriteAdapter(
                    favoriteList.toMutableList(),
                    onItemClick = { onItemClick(it) },
//                    onAddToCartClick = { onAddToCartClick(it) },
//                    onDeleteClick = { onDeleteClick(it) }
                )
                adapter = favoriteAdapter

                favoriteAdapter.onAddToCartClick = { onAddToCartClick(it) }
                favoriteAdapter.onDeleteClick = { onDeleteClick(it) }
            }
            if (it.size == 0) binding.loadingTip.showEmpty()
        }
    }

    fun onItemClick(favorite: Favorite) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("id", favorite.id)
        startActivity(intent)
    }

    fun onAddToCartClick(favorite: Favorite) {
        mainVm.addToCart(
            Cart(
                favorite.id,
                favorite.title,
                favorite.description,
                favorite.price,
                favorite.rating,
                favorite.thumbnail
            )
        )
    }

    fun onDeleteClick(favorite: Favorite) {
        mainVm.removeFromFavorites(favorite)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }
}