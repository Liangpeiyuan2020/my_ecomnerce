package com.zs.my_ecommerce.adapt

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.databinding.ItemCategoryHeadBinding
import com.zs.my_ecommerce.utils.BaseApp

class CategoryAdapter(val categories: List<String>, val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var selectionIndex = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding =
            ItemCategoryHeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(categories[position], position)
    }

    override fun getItemCount() = categories.size

    inner class CategoryViewHolder(val binding: ItemCategoryHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String, position: Int) {
            binding.buttonEachCategory.setText(category)
            binding.buttonEachCategory.setOnClickListener {
                selectionIndex = position
                onItemClick(category)
                notifyItemChanged(position)
            }
            if (position == selectionIndex) {
                binding.buttonEachCategory.setBackgroundColor(Color.parseColor("#ff8f00"))
                binding.buttonEachCategory.setTextColor(
                    ContextCompat.getColor(
                        BaseApp.getContext(),
                        R.color.white
                    )
                )
            } else {
                binding.buttonEachCategory.setBackgroundColor(Color.parseColor("#ffffff"))
                binding.buttonEachCategory.setTextColor(
                    ContextCompat.getColor(
                        BaseApp.getContext(),
                        R.color.black
                    )
                )
            }
        }
    }
}