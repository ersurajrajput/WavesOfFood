package com.example.wavesoffood.ui.res.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adepters.ResFoodItemsAdepter
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.Helpers.SharedPrefHelper
import com.example.wavesoffood.Models.FoodItemModel
import com.example.wavesoffood.databinding.FragmentResFoodListBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.initialize

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResFoodListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResFoodListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentResFoodListBinding
    private lateinit var adapter: ResFoodItemsAdepter

    private val foodList = ArrayList<FoodItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResFoodListBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Firebase.initialize(requireContext())
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val dbFoodItemRef = db.getReference("foodItem")

        // Setup RecyclerView & Adapter


        val tabLayout = binding.tabItem
        tabLayout.addTab(tabLayout.newTab().setText("ALL"))
        tabLayout.addTab(tabLayout.newTab().setText("Breakfast"))
        tabLayout.addTab(tabLayout.newTab().setText("Lunch"))
        tabLayout.addTab(tabLayout.newTab().setText("Dinner"))

        getAll()


        adapter = ResFoodItemsAdepter(requireContext(), foodList){currentItem,s->
            if (s == 1){
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Delete") { _, _ ->
                        Toast.makeText(requireContext(), "Deleted "+currentItem.foodName, Toast.LENGTH_SHORT).show()
                        dbFoodItemRef.child(currentItem.id.toString()).removeValue()
                    }
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                    .show()

            }else if (s == 0){
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to Edit this item?")
                    .setPositiveButton("Edit") { _, _ ->
                        Toast.makeText(requireContext(), "Deleted "+currentItem.foodName, Toast.LENGTH_SHORT).show()
                        dbFoodItemRef.child(currentItem.id.toString()).removeValue()
                    }
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                    .show()

            }

        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter






        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> getAll()
                    1 -> getBreakfast()
                    2 -> getLunch()
                    3 -> getDinner()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }




    private fun getAll(){
        Firebase.initialize(requireContext())
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val dbFoodItemRef = db.getReference("foodItem")
        val sharedPrefHelper = SharedPrefHelper(requireContext())

        dbFoodItemRef.orderByChild("resId").equalTo(sharedPrefHelper.getResId()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   foodList.clear()
                   for (snap in snapshot.children){
                       var foodItemModel = snap.getValue(FoodItemModel::class.java)
                       if (foodItemModel != null){
                           foodList.add(foodItemModel)

                       }
                   }
                   adapter.notifyDataSetChanged()
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }
    private fun getBreakfast(){
        Firebase.initialize(requireContext())
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val dbFoodItemRef = db.getReference("foodItem")
        val sharedPrefHelper = SharedPrefHelper(requireContext())

        dbFoodItemRef.orderByChild("resId").equalTo(sharedPrefHelper.getResId()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    foodList.clear()
                    for (snap in snapshot.children){

                        var foodItemModel = snap.getValue(FoodItemModel::class.java)
                        if (foodItemModel!=null && foodItemModel.foodCat.equals("Breakfast")){
                            foodList.add(foodItemModel)

                        }
                    }
                    adapter.notifyDataSetChanged()
                    Log.d("mytest",foodList.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }
    private fun getLunch(){
        Firebase.initialize(requireContext())
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val dbFoodItemRef = db.getReference("foodItem")
        val sharedPrefHelper = SharedPrefHelper(requireContext())

        dbFoodItemRef.orderByChild("resId").equalTo(sharedPrefHelper.getResId()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    foodList.clear()
                    for (snap in snapshot.children){

                        var foodItemModel = snap.getValue(FoodItemModel::class.java)
                        if (foodItemModel!=null && foodItemModel.foodCat.equals("Lunch")){
                            foodList.add(foodItemModel)

                        }
                    }
                    adapter.notifyDataSetChanged()
                    Log.d("mytest",foodList.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }
    private fun getDinner(){
        Firebase.initialize(requireContext())
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val dbFoodItemRef = db.getReference("foodItem")
        val sharedPrefHelper = SharedPrefHelper(requireContext())

        dbFoodItemRef.orderByChild("resId").equalTo(sharedPrefHelper.getResId()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    foodList.clear()
                    for (snap in snapshot.children){

                        var foodItemModel = snap.getValue(FoodItemModel::class.java)
                        if (foodItemModel!=null && foodItemModel.foodCat.equals("Dinner")){
                            foodList.add(foodItemModel)

                        }
                    }
                    adapter.notifyDataSetChanged()
                    Log.d("mytest",foodList.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResFoodListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResFoodListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}