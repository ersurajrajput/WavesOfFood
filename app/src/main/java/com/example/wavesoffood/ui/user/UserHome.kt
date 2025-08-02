package com.example.wavesoffood.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.Adapters.CatAdapter
import com.example.wavesoffood.Adapters.ResAdapter
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.R
import com.example.wavesoffood.model.CategoriesModel
import com.example.wavesoffood.model.RestaurantModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class UserHome : AppCompatActivity() {
    private lateinit var resAdapter: ResAdapter
    private lateinit var catAdapter: CatAdapter
    private lateinit var resList: ArrayList<RestaurantModel>
    private lateinit var catList: ArrayList<CategoriesModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Local variables
        var et_search  = findViewById<EditText>(R.id.et_search)
        // Firebase Initialization
        FirebaseApp.initializeApp(applicationContext)
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val myResRef = db.getReference("res")
        val myCatRef = db.getReference("cat")

        // RecyclerView initialization
        val restorentRecyclerView: RecyclerView = findViewById(R.id.restorentRecyclerView)
        val catRecyclerView: RecyclerView = findViewById(R.id.categoryRecyclerView)
        var drawerLayout : DrawerLayout = findViewById(R.id.main)
        var ibMenu : ImageButton  = findViewById(R.id.ib_menu)

        resList = ArrayList()
        catList = ArrayList()
        resAdapter = ResAdapter(applicationContext, resList) { clickedRestaurant ->
            Toast.makeText(this, clickedRestaurant.toString(), Toast.LENGTH_SHORT).show()
        }

        catAdapter = CatAdapter(applicationContext, catList) { clickedCategory ->
            Toast.makeText(this, clickedCategory.toString(), Toast.LENGTH_SHORT).show()
        }

        restorentRecyclerView.adapter = resAdapter
        catRecyclerView.adapter = catAdapter


        myResRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                resList.clear()
                for (res in snapshot.children) {
                    val restaurant = res.getValue(RestaurantModel::class.java)
                    restaurant?.let {
                        resList.add(it)

                        resAdapter.notifyDataSetChanged()

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        myCatRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                catList.clear()
                for (cat in snapshot.children) {
                    val category = cat.getValue(CategoriesModel::class.java)
                    category?.let {
                        catList.add(it)

                        catAdapter.notifyDataSetChanged()

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        /// Search btn logic
        et_search.setOnClickListener {

            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }

        ibMenu.setOnClickListener {
            drawerLayout.open()
        }

    }
}