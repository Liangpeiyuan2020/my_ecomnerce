package com.zs.my_ecommerce.adapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.bean.Favorite
import com.zs.my_ecommerce.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    val favorites: List<Favorite>,
    val onItemClick: (Favorite) -> Unit,
    val onAddToCartClick: (Favorite) -> Unit,
    val onDeleteClick: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        holder.bind(favorites[position])
    }

    override fun getItemCount() = favorites.size

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.favorite = favorite
            binding.cardView.setOnClickListener {
                onItemClick(favorite)
            }
        }
    }
}