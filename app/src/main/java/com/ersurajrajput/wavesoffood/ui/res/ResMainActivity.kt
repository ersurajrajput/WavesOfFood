package com.ersurajrajput.wavesoffood.ui.res

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.adapters.res.PendingOrderAdapter
import com.ersurajrajput.wavesoffood.adapters.res.ReqOrdersAdapter
import com.ersurajrajput.wavesoffood.databinding.ActivityResMainBinding
import com.ersurajrajput.wavesoffood.databinding.BottomSheetOrdersBinding
import com.ersurajrajput.wavesoffood.databinding.LayoutOrderBinding
import com.ersurajrajput.wavesoffood.helpers.LoginHelper
import com.ersurajrajput.wavesoffood.helpers.ResSharedRefHelper
import com.ersurajrajput.wavesoffood.models.OrderModel
import com.ersurajrajput.wavesoffood.ui.comman.OnBoradingActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.math.log

class ResMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResMainBinding
    private lateinit var orderReqList: ArrayList<OrderModel>
    private lateinit var orderPendingList: ArrayList<OrderModel>
    private lateinit var orderList: ArrayList<OrderModel>

    private lateinit var reqOrderRecyclerView: RecyclerView
    private lateinit var pendingOrdersRecyclerView: RecyclerView
    private lateinit var resSharedRefHelper: ResSharedRefHelper
    private lateinit var loginHelper: LoginHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //init
        loginHelper = LoginHelper(this)
        resSharedRefHelper = ResSharedRefHelper(this)

        /// login chake
        loginHelper.LoggedIn()

        binding.llAddFoodItem.setOnClickListener {
            startActivity(Intent(this, ResAddFoodItemActivity::class.java))
        }
        binding.llAllFoodItems.setOnClickListener {
            startActivity(Intent(this, ResAllFoodItemsActivity::class.java))

        }
        binding.llProfile.setOnClickListener {
            startActivity(Intent(this, ResProfileActivity::class.java))

        }
        binding.llLogOut.setOnClickListener {
            resSharedRefHelper.clearRes()
            startActivity(Intent(this, OnBoradingActivity::class.java))
            finish()
        }

        ////// Order Req
        //prepare data
        var img = "https://images.unsplash.com/photo-1747654168933-a0a0c9d78d68?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"

        orderReqList = ArrayList()
        for (i in 1..5){
            orderReqList.add(OrderModel(
                foodImg = img,
                foodName = "pizaa",
                orderQuantity = 10,
                totalPrice = 100,
            ))
        }
        //prepare adapter
        val reqOrdersAdapter = ReqOrdersAdapter(this, orderReqList)
        //prepare bottomsheet view
        var reqBottomSheetDialog = BottomSheetDialog(this)
        var reqBottomSheetBinding = BottomSheetOrdersBinding.inflate(layoutInflater)
        reqBottomSheetDialog.setContentView(reqBottomSheetBinding.root)
        binding.llOrderReq.setOnClickListener {
            // prepare recycler
            reqBottomSheetBinding.tvTotal.text = orderReqList.size.toString()
            reqBottomSheetBinding.ordersRecyclerView.layoutManager = LinearLayoutManager(this)
            reqBottomSheetBinding.ordersRecyclerView.adapter = reqOrdersAdapter
            reqBottomSheetDialog.show()


        }


        //// Orders (dispatch)
        val pendingOrderAdapter = PendingOrderAdapter(this, orderReqList)
        var pengindBottomSheetDialog = BottomSheetDialog(this)
        var pendingBottomSheetBinding = BottomSheetOrdersBinding.inflate(layoutInflater)
        pengindBottomSheetDialog.setContentView(pendingBottomSheetBinding.root)
        binding.llOrderDispatch.setOnClickListener {
            pendingBottomSheetBinding.tvTotal.text = orderReqList.size.toString()
            pendingBottomSheetBinding.ordersRecyclerView.layoutManager = LinearLayoutManager(this)
            pendingBottomSheetBinding.ordersRecyclerView.adapter = pendingOrderAdapter
            pengindBottomSheetDialog.show()

        }

        // all orders






    }
}