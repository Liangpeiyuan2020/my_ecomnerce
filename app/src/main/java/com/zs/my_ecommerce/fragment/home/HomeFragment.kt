package com.zs.my_ecommerce.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.adapt.ProductAdapter
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentHomeBinding
    private lateinit var pAdapter: ProductAdapter

    private val homeVm: HomeViewModel by viewModels {
        HomeViewModelFactory(MyDataBase.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        homeVm.getFavorites()
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
            Log.i("home5", it[0].title)
            pAdapter = ProductAdapter(
                homeVm.favorite.value ?: emptyMap(),
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
//        Log.i("homeFragment", "onItemClick")
    }

    private fun onFavoriteClick(product: Product) {
        if (homeVm.favorite.value!!.contains(product.id)) {
            homeVm.removeFavorite(product)
//            Log.i("homeFragment", "remove")
        } else {
            homeVm.addFavorite(product)
//            Log.i("homeFragment", "add")
        }

    }

    private fun onAddToCartClick(product: Product) {
        homeVm.getFavorites()
        Log.i("homeFragment", homeVm.favorite.value?.size.toString())

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}