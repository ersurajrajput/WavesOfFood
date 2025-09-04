package com.ersurajrajput.wavesoffood.ui.res

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.adapters.OrdersAdapter
import com.ersurajrajput.wavesoffood.databinding.ActivityOrderHistoryBinding
import com.ersurajrajput.wavesoffood.models.OrderModel

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var adapter: OrdersAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderList: ArrayList<OrderModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // init
        orderList = ArrayList()

        // prepare data
        var img = "https://images.unsplash.com/photo-1747654168933-a0a0c9d78d68?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"


        for (i in 1..5){
            orderList.add(OrderModel(
                foodImg = img,
                foodName = "pizaa",
                orderQuantity = 10,
                totalPrice = 100,
                orderStatus = "req"
            ))
        }
        for (i in 1..5){
            orderList.add(OrderModel(
                foodImg = img,
                foodName = "pizaa",
                orderQuantity = 10,
                totalPrice = 100,
                orderStatus = "running"
            ))
        }
        for (i in 1..5){
            orderList.add(OrderModel(
                foodImg = img,
                foodName = "pizaa",
                orderQuantity = 10,
                totalPrice = 100,
                orderStatus = "delivered"
            ))
        }
        for (i in 1..5){
            orderList.add(OrderModel(
                foodImg = img,
                foodName = "pizaa",
                orderQuantity = 10,
                totalPrice = 100,
                orderStatus = "cancel"
            ))
        }
    
        adapter = OrdersAdapter(this, orderList)
        recyclerView = binding.ordersHistoryRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter







    }
}