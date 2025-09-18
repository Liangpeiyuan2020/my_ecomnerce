package com.zs.my_ecommerce.adapt

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.zs.my_ecommerce.bean.Favorite
import com.zs.my_ecommerce.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    var favorites: MutableList<Favorite>,
    val onItemClick: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var onAddToCartClick: ((Favorite) -> Unit)? = null
    var onDeleteClick: ((Favorite) -> Unit)? = null
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
        holder.bind(favorites[position], position)


    }

    override fun getItemCount() = favorites.size

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite, position: Int) {
            binding.favorite = favorite
            binding.cardView.setOnClickListener {
                onItemClick(favorite)
            }
            binding.slideAddBtn.setOnClickListener {
                onAddToCartClick?.invoke(favorite)
                binding.swipeLayout.close(true)
            }
            binding.slideDeleteBtn.setOnClickListener {
                onDeleteClick?.invoke(favorite)
                favorites.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, favorites.size)
                binding.swipeLayout.close(true)
            }
            // 监听滑动状态变化
            binding.swipeLayout.setSwipeListener(object : SwipeRevealLayout.SwipeListener {
                override fun onClosed(view: SwipeRevealLayout?) {
                    Log.i("swipeLayout1", "")
                }

                override fun onOpened(view: SwipeRevealLayout?) {
                    Log.i("swipeLayout2", "")
                }

                override fun onSlide(view: SwipeRevealLayout?, slideOffset: Float) {
                    Log.i("swipeLayout3", "")
                }
            })
        }
    }

    // 添加关闭所有滑动项的方法
    fun closeAllItems(recyclerView: RecyclerView) {
        for (i in 0 until recyclerView.childCount) {
            val view = recyclerView.getChildAt(i)
            val holder = recyclerView.getChildViewHolder(view) as? FavoriteViewHolder
            holder?.binding?.swipeLayout?.close(true)
        }
    }

}