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

class ResAddFoodItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResAddFoodItemBinding
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
                var myFoodItem = FoodItemModel("1",foodName,"",foodPrice.toInt(),foodDescription,1.toFloat(),foodIngredients);
                Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this).setTitle("Success").setMessage("Item Added Successfully").setPositiveButton("Ok"){dialog, which ->
                    dialog.dismiss()
                    onBackPressedDispatcher.onBackPressed()
                }.show()
            }
        binding.ivFoodImg.setOnClickListener {
            pickImageLauncher.launch("image/*")

        }





    }
//    fun permissionReq()
}