package com.zs.my_ecommerce.fragment.favorite

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
import com.zs.my_ecommerce.adapt.FavoriteAdapter
import com.zs.my_ecommerce.bean.Favorite
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteVm: FavoriteViewModel by viewModels()
    private val mainVm: MainViewModel by activityViewModels {
        MainViewModelFactory(MyDataBase.getInstance())
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        mainVm.getFavorites()
    }

    private fun observe() {
        mainVm.favorites.observe(viewLifecycleOwner) {
            val favoriteList = it.values.toList()
            binding.recycleView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = FavoriteAdapter(
                    favoriteList,
                    onItemClick = { onItemClick(it) },
                    onAddToCartClick = { onAddToCartClick(it) },
                    onDeleteClick = { onDeleteClick(it) })
            }
        }
    }

    fun onItemClick(favorite: Favorite) {

    }

    fun onAddToCartClick(favorite: Favorite) {

    }

    fun onDeleteClick(favorite: Favorite) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }
}