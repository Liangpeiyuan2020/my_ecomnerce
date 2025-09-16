package com.zs.my_ecommerce.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
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
        observe()
        homeVm.getFavorites()
        homeVm.getProducts()
    }

    private fun initRecycle() {

    }

    private fun observe() {
        homeVm.product.observe(viewLifecycleOwner) {
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