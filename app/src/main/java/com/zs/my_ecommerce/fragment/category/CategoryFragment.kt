package com.zs.my_ecommerce.fragment.category

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.adapt.CategoryAdapter
import com.zs.my_ecommerce.adapt.ProductAdapter
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.FragmentCategoryBinding
import com.zs.my_ecommerce.databinding.FragmentHomeBinding

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val categoryVm: CategoryViewModel by viewModels {
        CategoryViewModelFactory(MyDataBase.getInstance())
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private val viewModel: CategoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        loadData()
    }

    private fun loadData() {
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
                    emptyMap(),
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

    }

    fun onFavoriteClick(product: Product) {

    }

    fun onAddToCartClick(product: Product) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_category, container, false)
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }
}