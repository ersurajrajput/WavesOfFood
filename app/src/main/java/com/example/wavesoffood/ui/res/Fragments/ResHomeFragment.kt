package com.example.wavesoffood.ui.res.Fragments

import ReqOrdersAdepter
import RunningOrdersAdepter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.Helpers.SharedPrefHelper
import com.example.wavesoffood.Models.OrderModel
import com.example.wavesoffood.Models.ResModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentResHomeBinding
import com.example.wavesoffood.ui.res.ResHomeActivity
import com.example.wavesoffood.ui.res.ResProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
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
 * Use the [ResHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentResHomeBinding


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
        binding = FragmentResHomeBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.initialize(requireContext())
        var db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        var dbOrdersRef = db.getReference("orders")
        var dbResRef = db.getReference("res")
        val queryRunningOrders = dbOrdersRef.orderByChild("orderStatus").equalTo("running")
        val queryReqOrders = dbOrdersRef.orderByChild("orderStatus").equalTo("req")
        var tvMainTotalNoOfRunningOrders = binding.tvNoOfRunningOrders
        var tvMainTotalNoOfReqOrders = binding.tvTotalNoOfReqOrders
        var sharedPrefHelper = SharedPrefHelper(requireContext())
        var resId = sharedPrefHelper.getResId()


        dbResRef.child(resId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   var resModel = snapshot.getValue(ResModel::class.java)
                   tvMainTotalNoOfRunningOrders.text = resModel?.resTotalRunningOrders.toString()
                   tvMainTotalNoOfReqOrders.text = resModel?.resTotalReqOrders.toString()

               }else{
                   Toast.makeText(requireContext(),"No Orders", Toast.LENGTH_SHORT).show()
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })















        // Drawer Logic
        binding.ibDrawer.setOnClickListener {
            Toast.makeText(requireContext(), "Drawer Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), ResProfileActivity::class.java)
            startActivity(intent)
        }








        //running order logic
        binding.rlRunningOrders.setOnClickListener {
            var bottomSheetDialog = BottomSheetDialog(requireContext())
            val runningOrders = layoutInflater.inflate(R.layout.running_orders_layout, null)
            bottomSheetDialog.setContentView(runningOrders)
            bottomSheetDialog.show()

            var rvRuningOrders = runningOrders.findViewById<RecyclerView>(R.id.runningOrdersRecyclerView)
            rvRuningOrders.layoutManager = LinearLayoutManager(requireContext())
            var totalRunningOrders : String? = null
            val orderList = ArrayList<OrderModel>()
            val runningadapter = RunningOrdersAdepter(requireContext(),orderList)
            rvRuningOrders.adapter = runningadapter
            queryRunningOrders.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        orderList.clear()
                        for (orderSnap in snapshot.children) {
                            val order = orderSnap.getValue(OrderModel::class.java)
                            order?.let { orderList.add(it) }
                        }
                        runningadapter.notifyDataSetChanged()
                        totalRunningOrders = orderList.size.toString()

                        bottomSheetDialog.findViewById<TextView>(R.id.tvTotalNoOfRunningOrders)?.text = totalRunningOrders
                    }
                    if (!snapshot.exists()) {
                        Toast.makeText(requireContext(), "No orders", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


            var btnDismiss = runningOrders.findViewById<Button>(R.id.btnDismiss)
            btnDismiss.setOnClickListener{
                bottomSheetDialog.hide()
            }
        }

        //req orderLogic
        binding.rlReqOrders.setOnClickListener {

            var bottomSheetDialog = BottomSheetDialog(requireContext())
            val reqOrders = layoutInflater.inflate(R.layout.req_orders_layout, null)
            bottomSheetDialog.setContentView(reqOrders)
            bottomSheetDialog.show()

            var rvReqOrders = reqOrders.findViewById<RecyclerView>(R.id.reqOrdersRecyclerView)
            rvReqOrders.layoutManager = LinearLayoutManager(requireContext())
            var totalReqOrders : String? = null
            val orderList = ArrayList<OrderModel>()
            val runningadapter = ReqOrdersAdepter(requireContext(),orderList)
            rvReqOrders.adapter = runningadapter
            queryReqOrders.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        orderList.clear()
                        for (orderSnap in snapshot.children) {
                            val order = orderSnap.getValue(OrderModel::class.java)
                            order?.let { orderList.add(it) }
                        }
                        runningadapter.notifyDataSetChanged()
                        totalReqOrders = orderList.size.toString()

                        bottomSheetDialog.findViewById<TextView>(R.id.tvTotalNoOfRunningOrders)?.text = totalReqOrders                    }
                    if (!snapshot.exists()) {
                        Toast.makeText(requireContext(), "No orders", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


            var btnDismiss = reqOrders.findViewById<Button>(R.id.btnDismiss)
            btnDismiss.setOnClickListener{
                bottomSheetDialog.hide()
            }


        }

        //req order logic

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}