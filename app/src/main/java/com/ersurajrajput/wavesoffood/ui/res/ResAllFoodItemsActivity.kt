package com.ersurajrajput.wavesoffood.ui.res

import android.os.Bundle
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

class ResAllFoodItemsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResAllFoodItemsBinding
    private lateinit var foodItemList: ArrayList<FoodItemModel>
    private lateinit var resAllFoodItemAdapter: ResAllFoodItemAdapter
    private lateinit var resAllFoodItemRecyclerView: RecyclerView
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
        // sample data
        var img = "https://images.unsplash.com/photo-1747654168933-a0a0c9d78d68?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        foodItemList = ArrayList()

        for (i in 1..10) {
            foodItemList.add(
                FoodItemModel(
                    foodId = "1",
                    foodName = "pizaa",
                    foodImg = img,
                    resName = "Ladli Restaurant",
                    foodPrice = 70
                )
            )

        }
        // prepare adapter
        resAllFoodItemAdapter = ResAllFoodItemAdapter(foodItemList, this)

        // prepare recycler view
        resAllFoodItemRecyclerView = binding.resAllFoodItemRecyclerView
        resAllFoodItemRecyclerView.layoutManager = LinearLayoutManager(this)
        resAllFoodItemRecyclerView.adapter = resAllFoodItemAdapter
    }
}