package com.example.wavesoffood.ui.user

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.Adapters.CatAdapter
import com.example.wavesoffood.Adapters.ResAdapter
import com.example.wavesoffood.R
import com.example.wavesoffood.model.CategoriesModel
import com.example.wavesoffood.model.RestaurantModel
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val restorentRecyclerView : RecyclerView = findViewById(R.id.restorentRecyclerView)
        val catRecyclerView : RecyclerView = findViewById(R.id.categoryRecyclerView)
        val catList = ArrayList<CategoriesModel>()
        val resList = ArrayList<RestaurantModel>()




        for(i in 1..5){
            val cimg = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=999&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            catList.add(CategoriesModel( i.toString(),"Burgers",cimg))
            val rimg :String = "https://images.unsplash.com/photo-1514933651103-005eec06c04b?q=80&w=1074&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            resList.add(RestaurantModel(i.toString(),"Ladli Restorent","","Samosa - Pizza - Dosa","4.7","Free","20 min"))
        }






        catRecyclerView.adapter = CatAdapter(applicationContext,catList){ clickedCategory ->
            Toast.makeText(applicationContext, clickedCategory.toString(), Toast.LENGTH_SHORT).show()
        }
        restorentRecyclerView.adapter = ResAdapter(applicationContext,resList){ clickedCategory ->
            Toast.makeText(applicationContext, clickedCategory.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}