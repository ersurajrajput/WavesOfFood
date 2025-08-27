package com.ersurajrajput.wavesoffood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.adapters.PopularFoodAdapter
import com.ersurajrajput.wavesoffood.databinding.FragmentHomeBinding
import com.ersurajrajput.wavesoffood.models.FoodItemModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imgList = ArrayList<SlideModel>()
        imgList.add(SlideModel(R.drawable.img_banner1))
        imgList.add(SlideModel(R.drawable.img_banner2))
        imgList.add(SlideModel(R.drawable.img_banner3))

        binding.imageSlider.setImageList(imgList, ScaleTypes.FIT)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // Handle image click here
                Toast.makeText(requireContext(), "Clicked Banner "+position.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun doubleClick(position: Int) {
            }
        })


        // prepare data
        var img = "https://images.unsplash.com/photo-1747654168933-a0a0c9d78d68?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        var foodList = ArrayList<FoodItemModel>()

        for (i in 1..5){
            foodList.add(FoodItemModel(i.toString(),"Pizaa "+i.toString(),img,10.toString()))
        }

        //prepare adapter
        var popularFoodAdapter = PopularFoodAdapter(foodList,requireContext())
        //prepare recycler view
        binding.popularFoodRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularFoodRecyclerView.adapter = popularFoodAdapter


        //bottomSheetLogic
        // target inside elements
        var bottomSheetDialog = BottomSheetDialog(requireContext())
        //inflate layout
        var view = layoutInflater.inflate(R.layout.layout_view_menu,null)
        bottomSheetDialog.setContentView(view)
        var viewMenuRecyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.viewMenuRecyclerView)
        //set recycler view
        viewMenuRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        //set adapter
        viewMenuRecyclerView?.adapter = popularFoodAdapter
        bottomSheetDialog?.findViewById<TextView>(R.id.tvTotalFoodItems)?.text = popularFoodAdapter.itemCount.toString()
        binding.tvViewMenu.setOnClickListener {
            bottomSheetDialog.show()
        }


    }

    companion object {

    }
}