package com.zs.my_ecommerce.adapt

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.common.SlidingScrollView
import com.zs.my_ecommerce.databinding.ItemFavoriteOldBinding

class SlideFavoriteAdapter(private val data: MutableList<String?>) :
    RecyclerView.Adapter<SlideFavoriteAdapter.SlideFavoriteViewHolder?>() {
    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideFavoriteViewHolder {
        val binding =
            ItemFavoriteOldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlideFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideFavoriteViewHolder, position: Int) {
        val item = data.get(position)

//
//        // 重置滑动状态
//        if (position == expandedPosition) {
//            holder.slidingScrollView.expandButtons()
//        } else {
//            holder.slidingScrollView.collapseButtons()
//        }


//        // 内容区域点击事件
//        holder.contentLayout.setOnClickListener(View.OnClickListener { v: View? ->
//            if (expandedPosition == position) {
//                expandedPosition = -1
//                notifyItemChanged(position)
//            } else {
//                val prevExpanded = expandedPosition
//                expandedPosition = position
//                notifyItemChanged(prevExpanded)
//                notifyItemChanged(position)
//            }
//        })


//        // 编辑按钮点击事件
//        holder.btnEdit.setOnClickListener(View.OnClickListener { v: View? ->
//            // 处理编辑操作
//            Toast.makeText(v!!.getContext(), "编辑: " + item, Toast.LENGTH_SHORT).show()
//            collapseItem(position)
//        })
//
//
//        // 删除按钮点击事件
//        holder.btnDelete.setOnClickListener(View.OnClickListener { v: View? ->
//            // 处理删除操作
//            Toast.makeText(v!!.getContext(), "删除: " + item, Toast.LENGTH_SHORT).show()
//            collapseItem(position)
//        })
    }

    private fun collapseItem(position: Int) {
        expandedPosition = -1
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class SlideFavoriteViewHolder(val binding: ItemFavoriteOldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var slidingScrollView: SlidingScrollView
        var contentLayout: View
        var btnEdit: TextView
        var btnDelete: TextView

        init {
            slidingScrollView = binding.horizontalScrollView
            contentLayout = binding.cardView
            btnEdit = binding.slideAddBtn
            btnDelete = binding.slideDeleteBtn
        }

        
    }
}