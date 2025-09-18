package com.zs.my_ecommerce.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.activity.MainViewModel
import com.zs.my_ecommerce.activity.MainViewModelFactory
import com.zs.my_ecommerce.activity.ProductDetailActivity
import com.zs.my_ecommerce.adapt.ProductAdapter
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
//        fun newInstance(param1: String, param2: String) =
//            HomeFragment().apply {
//                arguments = Bundle().apply {
//                    putString("key1", param1)
//                    putString("key2", param2)
//                }
//            }

    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var pAdapter: ProductAdapter

    private val homeVm: HomeViewModel by viewModels()
    private val mainVm: MainViewModel by activityViewModels {
        MainViewModelFactory(MyDataBase.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.i("HomeFragment", "onViewCreated")
        initRecycle()
        initSearchBar()
        observe()
        mainVm.getFavorites()
        homeVm.getProducts()
    }

    private fun initSearchBar() {
        binding.searchBarProduct.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query.length != 0) {
                    Log.i("home1", query ?: "not")
                    homeVm.search(query)
                } else {
                    Log.i("home2", query ?: "not")
                    homeVm.getProducts()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length != 0) {
                    Log.i("home3", newText ?: "not")
                    homeVm.search(newText)
                } else {
                    Log.i("home4", newText ?: "not")
                    homeVm.getProducts()
                }
                return true
            }

        })
    }

    private fun initRecycle() {
        var state = 1
//        0，表示当前没有滚动，处于空闲状态。
//        1，表示用户正在拖动RecyclerView进行滚动。
//        2，表示RecyclerView正在依靠惯性滑动
        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                state = newState
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && (state == 0 || state == 2)) {
                    binding.collapsingToolBarLayout.visibility = View.GONE

                } else if (dy < -10) {
                    binding.collapsingToolBarLayout.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observe() {
        homeVm.product.observe(viewLifecycleOwner) {
            pAdapter = ProductAdapter(
                mainVm.favorites.value ?: emptyMap(),
                homeVm.product.value ?: emptyList(),
                onItemClick = { product -> onItemClick(product) },
                onFavoriteClick = { product -> onFavoriteClick(product) },
                onAddToCartClick = { product -> onAddToCartClick(product) }
            )
            binding.recycleView.apply {
                layoutManager = GridLayoutManager(requireActivity(), 2)
                adapter = pAdapter
            }
        }
    }

    private fun onItemClick(product: Product) {
        Log.i("homeFragment", "onItemClick")
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("id", product.id)
        startActivity(intent)
    }

    private fun onFavoriteClick(product: Product) {
        mainVm.favoriteOperator(product)
    }

    private fun onAddToCartClick(product: Product) {
        mainVm.addToCart(
            Cart(
                product.id,
                product.title,
                product.description,
                product.price,
                product.rating,
                product.thumbnail
            )
        )

    }

}