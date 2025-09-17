package com.zs.my_ecommerce.adapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.databinding.ItemCartBinding

class CartAdapter(
    val carts: List<Cart>,
    onPlusClick: (Cart) -> Unit,
    onMinusClick: (Cart) -> Unit,
    onRemoveClick: (Cart) -> Unit,
    onItemClick: (Cart) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        holder.bind(carts[position])
    }

    override fun getItemCount() = carts.size

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart) {
            binding.cart = cart
        }
    }
}