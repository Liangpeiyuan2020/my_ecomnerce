package com.zs.my_ecommerce.fragment.cart

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.activity.MainViewModel
import com.zs.my_ecommerce.activity.MainViewModelFactory
import com.zs.my_ecommerce.activity.ProductDetailActivity
import com.zs.my_ecommerce.adapt.CartAdapter
import com.zs.my_ecommerce.bean.Cart
import com.zs.my_ecommerce.common.observeOnce
import com.zs.my_ecommerce.dataBase.MyDataBase
import com.zs.my_ecommerce.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private val cartVm: CartViewModel by viewModels()
    private val mainVm: MainViewModel by activityViewModels {
        MainViewModelFactory(MyDataBase.getInstance())
    }
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    companion object {
        fun newInstance() = CartFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        observe()
        loadDate()
    }

    private fun onClick() {
        binding.recycleView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // 当手指按下时，关闭所有打开的滑动项
                        cartAdapter.closeAllItems(binding.recycleView)
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    private fun loadDate() {
        mainVm.getCarts()
    }

    private fun observe() {
        mainVm.carts.observeOnce(viewLifecycleOwner) {
            binding.recycleView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                cartAdapter = CartAdapter(
                    it.toMutableList(),
                    onPlusClick = { onPlusClick(it) },
                    onMinusClick = { onMinusClick(it) },
                    onRemoveClick = { onRemoveClick(it) },
                    onItemClick = { onItemClick(it) }
                )
                adapter = cartAdapter
            }
        }
        mainVm.carts.observe(viewLifecycleOwner) {
            val totalPrice: Float = it.sumOf { (it.price * it.num).toDouble() }.toFloat()
            binding.totalAmount.text = totalPrice.toString()
            if (it.size == 0) binding.loadingTip.showEmpty()
            else binding.loadingTip.dismiss()
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
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("id", cart.id)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }
}