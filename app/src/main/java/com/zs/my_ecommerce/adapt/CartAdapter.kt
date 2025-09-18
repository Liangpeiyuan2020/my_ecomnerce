package com.zs.my_ecommerce.adapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.databinding.ItemCartBinding

class CartAdapter(
    var carts: MutableList<Cart>,
    val onPlusClick: (Cart) -> Unit,
    val onMinusClick: (Cart) -> Unit,
    val onRemoveClick: (Cart) -> Unit,
    val onItemClick: (Cart) -> Unit
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
        holder.bind(carts[position], position)

    }

    override fun getItemCount() = carts.size

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart, position: Int) {
            binding.cart = cart
            binding.minusButton.setOnClickListener {
                val num = cart.num
                onMinusClick(cart)
                if (num == 1) {
                    carts.removeAt(position)
                    notifyItemChanged(position)
                    notifyItemRangeChanged(position, carts.size)
                } else {
                    cart.num -= 1
                    notifyItemChanged(position)
                }
            }
            binding.plusButton.setOnClickListener {
                onPlusClick(cart)
                cart.num += 1
                notifyItemChanged(position)
            }
            binding.cardView.setOnClickListener {
                onItemClick(cart)
            }
        }
    }
}