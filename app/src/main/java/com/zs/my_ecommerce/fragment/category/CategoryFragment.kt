package com.zs.my_ecommerce.fragment.category

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.activity.MainViewModel
import com.zs.my_ecommerce.activity.MainViewModelFactory
import com.zs.my_ecommerce.activity.ProductDetailActivity
import com.zs.my_ecommerce.adapt.CategoryAdapter
import com.zs.my_ecommerce.adapt.ProductAdapter
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.FragmentCategoryBinding
import com.zs.my_ecommerce.databinding.FragmentHomeBinding

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val categoryVm: CategoryViewModel by viewModels()
    private val mainVm: MainViewModel by activityViewModels {
        MainViewModelFactory(MyDataBase.getInstance())
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        loadData()
    }

    private fun loadData() {
        mainVm.getFavorites()
        categoryVm.getCategories()
        categoryVm.getCategorizedProduct("beauty")
    }

    private fun observe() {
        categoryVm.categories.observe(viewLifecycleOwner) {
            binding.horizontalRecycleView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = CategoryAdapter(it, onItemClick = { onCategoryItemClick(it) })
            }
        }
        categoryVm.products.observe(viewLifecycleOwner) {
            binding.verticalRecycleView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = ProductAdapter(
                    mainVm.favorites.value ?: emptyMap(),
                    it,
                    onItemClick = { product -> onItemClick(product) },
                    onFavoriteClick = { product -> onFavoriteClick(product) },
                    onAddToCartClick = { product -> onAddToCartClick(product) }
                )
            }
        }
    }

    fun onCategoryItemClick(category: String) {
        categoryVm.getCategorizedProduct(category)
    }

    fun onItemClick(product: Product) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("id", product.id)
        startActivity(intent)
    }

    fun onFavoriteClick(product: Product) {
        mainVm.favoriteOperator(product)
    }

    fun onAddToCartClick(product: Product) {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }
}