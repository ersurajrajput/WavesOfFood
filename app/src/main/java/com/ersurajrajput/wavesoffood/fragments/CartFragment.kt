package com.ersurajrajput.wavesoffood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.adapters.CartItemAdapter
import com.ersurajrajput.wavesoffood.databinding.FragmentCartBinding
import com.ersurajrajput.wavesoffood.models.CartItemModel


class CartFragment : Fragment() {
   private lateinit var binding: FragmentCartBinding
   private lateinit var cartItemList: ArrayList<CartItemModel>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // prepare data
        cartItemList = ArrayList()
        var img = "https://images.unsplash.com/photo-1747654168933-a0a0c9d78d68?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"

        for (i in 1..5){
            cartItemList.add(CartItemModel(foodName = "pizaa", foodImg = img, foodPrice = 10, foodQuantity = 1))
        }

        // prepare recycler view
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // prepare adapter
        var cartItemAdapter = CartItemAdapter(cartItemList,requireContext())

        //link both
        binding.cartRecyclerView.adapter = cartItemAdapter

    }




    companion object {

    }
}