package com.example.wavesoffood.ui.user

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.Adapters.RecentKeywordsAdepter
import com.example.wavesoffood.Adapters.ResAdapter
import com.example.wavesoffood.Adapters.SuggestedRestaurantsAdapter
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.R
import com.example.wavesoffood.model.CategoriesModel
import com.example.wavesoffood.model.RecentKeywordsModel
import com.example.wavesoffood.model.RestaurantModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SearchActivity : AppCompatActivity() {
    private lateinit var recentKeywordsAdepter: RecentKeywordsAdepter
    private lateinit var suggestedRestaurantsAdapter: SuggestedRestaurantsAdapter
    private lateinit var keywordList: ArrayList<RecentKeywordsModel>
    private lateinit var resList: ArrayList<RestaurantModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //db setup
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val myResRef = db.getReference("res")
        // initialize local variable
        val keywordRecyclerView = findViewById<RecyclerView>(R.id.recentKeywordsRecyclerView)
        val suggestedRestaurantsRecyclerView = findViewById<RecyclerView>(R.id.suggestedRestaurantsRecyclerView)
        keywordList = ArrayList()
        resList = ArrayList()


        suggestedRestaurantsAdapter = SuggestedRestaurantsAdapter(applicationContext, resList) { clickedItem ->
            Toast.makeText(this, clickedItem.toString(), Toast.LENGTH_SHORT).show()
        }
        recentKeywordsAdepter = RecentKeywordsAdepter(applicationContext, keywordList) { clickedKeyword ->
            Toast.makeText(this, clickedKeyword.toString(), Toast.LENGTH_SHORT).show()
        }

        keywordRecyclerView.adapter = recentKeywordsAdepter
        suggestedRestaurantsRecyclerView.adapter = suggestedRestaurantsAdapter

        for (i in 1..5){
            keywordList.add(RecentKeywordsModel("Pizza "+i.toString()))
            recentKeywordsAdepter.notifyDataSetChanged()
        }

        myResRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                resList.clear()
                for (res in snapshot.children) {
                    val restaurant = res.getValue(RestaurantModel::class.java)
                    restaurant?.let {
                        resList.add(it)

                        suggestedRestaurantsAdapter.notifyDataSetChanged()

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}