package com.zs.my_ecommerce.fragment.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zs.my_ecommerce.R
import com.zs.my_ecommerce.common.MyNavigationController
import com.zs.my_ecommerce.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private var navigationController: MyNavigationController? = null

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() {
        binding.ivHead.setOnClickListener {
            Toast.makeText(context, "功能开发中，敬请期待", Toast.LENGTH_LONG).show()
        }
        binding.tvName.setOnClickListener {
            Toast.makeText(context, "功能开发中，敬请期待", Toast.LENGTH_LONG).show()
        }
        binding.tvId.setOnClickListener {
            Toast.makeText(context, "功能开发中，敬请期待", Toast.LENGTH_LONG).show()
        }
        binding.tvLogout.setOnClickListener {
            Toast.makeText(context, "功能开发中，敬请期待", Toast.LENGTH_LONG).show()
        }
        binding.clCollect.setOnClickListener {
            navigationController?.navigateToTab(R.id.favorite)
        }
        binding.clArticle.setOnClickListener {
            navigationController?.navigateToTab(R.id.cart)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MyNavigationController) {
            navigationController = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }
}