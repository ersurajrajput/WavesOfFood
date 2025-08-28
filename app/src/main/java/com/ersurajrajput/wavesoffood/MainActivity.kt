package com.ersurajrajput.wavesoffood

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersurajrajput.wavesoffood.adapters.NotificationAdapter
import com.ersurajrajput.wavesoffood.databinding.ActivityMainBinding
import com.ersurajrajput.wavesoffood.models.NotificationModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationList: ArrayList<NotificationModel>
    private lateinit var notificationAdapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        //notification logic
        var img = "https://images.unsplash.com/photo-1747654168933-a0a0c9d78d68?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"

        notificationList = ArrayList()

        for (i in 1..5){
            notificationList.add(NotificationModel(notificationTitle = "hello", notificationImg = img))
        }
        notificationAdapter = NotificationAdapter(this,notificationList)

        // target bottom sheet
        var notificationBottomSheet = BottomSheetDialog(this)
        var view = layoutInflater.inflate(R.layout.bottomsheet_notification,null)
        notificationBottomSheet.setContentView(view)
        var notificationRecyclerView = notificationBottomSheet.findViewById<RecyclerView>(R.id.notificationRecyclerView)
        //set recycler view
        notificationRecyclerView?.layoutManager = LinearLayoutManager(this)
        //set adapter
        notificationRecyclerView?.adapter = notificationAdapter
         binding.ivNotification.setOnClickListener {
             notificationBottomSheet.show()
         }

    }
}