package com.zs.my_ecommerce.adapt

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.bean.Favorite
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.databinding.ItemProductBinding

class ProductAdapter(
    val favorites: Map<Int, Favorite>,
    val products: List<Product>,
    val onItemClick: (Product) -> Unit,
    val onFavoriteClick: (Product) -> Unit,
    val onAddToCartClick: (Product) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount() = products.size


    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
            binding.checkbox.isChecked = favorites.contains(product.id)

            binding.cardView.setOnClickListener {
                onItemClick(product)
            }
            binding.checkbox.setOnClickListener {
                onFavoriteClick(product)
            }
            binding.buttonAddToCart.setOnClickListener {
                onAddToCartClick(product)
            }
        }
    }
}