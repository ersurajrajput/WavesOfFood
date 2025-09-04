package com.ersurajrajput.wavesoffood.ui.res

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.adapters.res.ResAllFoodItemAdapter
import com.ersurajrajput.wavesoffood.databinding.ActivityResAllFoodItemsBinding
import com.ersurajrajput.wavesoffood.models.FoodItemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResAllFoodItemsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResAllFoodItemsBinding
    private lateinit var foodItemList: ArrayList<FoodItemModel>
    private lateinit var resAllFoodItemAdapter: ResAllFoodItemAdapter
    private lateinit var resAllFoodItemRecyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResAllFoodItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // init
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        foodItemList = ArrayList()
        var uid = auth.currentUser?.uid
        var foodItemDbRef = database.getReference("FoodItems")
        var q = foodItemDbRef.orderByChild("resId").equalTo(uid)
        q.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    foodItemList.clear()
                    for (foodItemSnapshot in snapshot.children){
                        var foodItem = foodItemSnapshot.getValue(FoodItemModel::class.java)
                        foodItemList.add(foodItem!!)
                    }
                    resAllFoodItemAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@ResAllFoodItemsActivity,"No Food Items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





        // prepare adapter
        resAllFoodItemAdapter = ResAllFoodItemAdapter(foodItemList, this)

        // prepare recycler view
        resAllFoodItemRecyclerView = binding.resAllFoodItemRecyclerView
        resAllFoodItemRecyclerView.layoutManager = LinearLayoutManager(this)
        resAllFoodItemRecyclerView.adapter = resAllFoodItemAdapter
    }
}