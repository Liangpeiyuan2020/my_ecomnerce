package com.zs.my_ecommerce.adapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.bean.Product
import com.zs.my_ecommerce.databinding.ItemProductBinding

class ProductAdapter(
    val productList: List<Product>,
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
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount() = productList.size

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
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