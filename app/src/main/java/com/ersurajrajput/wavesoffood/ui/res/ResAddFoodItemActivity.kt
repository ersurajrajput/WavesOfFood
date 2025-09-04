package com.ersurajrajput.wavesoffood.ui.res

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.ActivityResAddFoodItemBinding
import com.ersurajrajput.wavesoffood.models.FoodItemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ResAddFoodItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResAddFoodItemBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.ivFoodImg.setImageURI(uri)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResAddFoodItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //init
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()




//
            binding.btnAdd.setOnClickListener {
                val foodName = binding.etFoodName.text.toString()
                val foodPrice = binding.etFoodPrice.text.toString()
                val foodDescription = binding.etFoodDescription.text.toString()
                val foodIngredients = binding.etFoodIngredients.text.toString()
                if (foodName.isEmpty() || foodPrice.isEmpty() || foodDescription.isEmpty() || foodIngredients.isEmpty()){
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                var img = "https://images.unsplash.com/photo-1747654168933-a0a0c9d78d68?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"


                var myFoodItem = FoodItemModel(
                    foodName = foodName,
                    foodPrice = foodPrice.toInt(),
                    foodDescription = foodDescription,
                    foodIngredients = foodIngredients,
                    foodImg = img,
                );

                saveFood(myFoodItem)

            }
        binding.ivFoodImg.setOnClickListener {
            pickImageLauncher.launch("image/*")

        }





    }
//    fun permissionReq()


    private fun saveFood(foodItemModel: FoodItemModel){
        var uid = auth.currentUser?.uid
        var foodItemDBRef = database.getReference("FoodItems")
        var key = foodItemDBRef.push().key?:return
        foodItemModel.foodId = key
        foodItemModel.resId = uid
        foodItemModel.resName = "suraj"
        foodItemDBRef.child(key).setValue(foodItemModel)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(this).setTitle("Success").setMessage("Item Added Successfully").setPositiveButton("Ok"){dialog, which ->
                        dialog.dismiss()
                        onBackPressedDispatcher.onBackPressed()
                    }.show()
                }else{
                    Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(this).setTitle("Failed").setMessage(task.exception?.message).setPositiveButton("Ok"){dialog, which ->
                        dialog.dismiss()

                    }.show()
                }
            }

    }
}