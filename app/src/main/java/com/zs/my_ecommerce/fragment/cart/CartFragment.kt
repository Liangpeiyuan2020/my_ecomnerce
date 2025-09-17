package com.zs.my_ecommerce.fragment.cart

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
import com.zs.my_ecommerce.adapt.CartAdapter
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private val cartVm: CartViewModel by viewModels()
    private val mainVm: MainViewModel by activityViewModels {
        MainViewModelFactory(MyDataBase.getInstance())
    }
    private lateinit var binding: FragmentCartBinding

    companion object {
        fun newInstance() = CartFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        loadDate()
    }

    private fun loadDate() {
        mainVm.getCarts()
    }

    private fun observe() {
        mainVm.carts.observe(viewLifecycleOwner) {
            binding.recycleView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = CartAdapter(
                    it.toMutableList(),
                    onPlusClick = { onPlusClick(it) },
                    onMinusClick = { onMinusClick(it) },
                    onRemoveClick = { onRemoveClick(it) },
                    onItemClick = {
                        onItemClick(it)
                    }
                )
            }
        }
    }

    fun onPlusClick(cart: Cart) {
        mainVm.addToCart(cart)
    }

    fun onMinusClick(cart: Cart) {
        mainVm.minusFromCart(cart)
    }

    fun onRemoveClick(cart: Cart) {
        mainVm.deleteFromCart(cart)
    }

    fun onItemClick(cart: Cart) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }
}