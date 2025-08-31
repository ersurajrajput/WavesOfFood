package com.ersurajrajput.wavesoffood.ui.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.adapters.PopularFoodAdapter
import com.ersurajrajput.wavesoffood.databinding.FragmentSearchBinding
import com.ersurajrajput.wavesoffood.models.FoodItemModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var foodList: ArrayList<FoodItemModel>
    private lateinit var originalFoodList: ArrayList<FoodItemModel>
    private lateinit var searchFoodAdapter: PopularFoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //prepare data
        foodList = ArrayList()
        originalFoodList = ArrayList()
        var img = "https://images.unsplash.com/photo-1747654168933-a0a0c9d78d68?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"


        for (i in 1..10){
            originalFoodList.add(FoodItemModel(i.toString(),"Pizaa "+i.toString(),img,10))
        }
        foodList.addAll(originalFoodList)

        //prepare adapter
        searchFoodAdapter = PopularFoodAdapter(foodList,requireContext())

        // prepare recycler view
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        //bind both
        binding.menuRecyclerView.adapter = searchFoodAdapter


        // search logic
        binding.btnSearch.setOnClickListener {
            var q = binding.etSearch.text.toString()
            foodList.clear()
            foodList.addAll(originalFoodList.filter {
                it.foodName?.contains(q,false) ?: false
            })
            searchFoodAdapter.notifyDataSetChanged()

        }


    }

    companion object {

    }
}