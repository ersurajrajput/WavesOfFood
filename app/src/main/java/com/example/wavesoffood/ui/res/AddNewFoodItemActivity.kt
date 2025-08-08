package com.example.wavesoffood.ui.res

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.Models.FoodItemModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityAddNewFoodItemBinding
import com.example.wavesoffood.databinding.ActivityResLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.database
import java.io.File

class AddNewFoodItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewFoodItemBinding
    private lateinit var imageUri: Uri
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                binding.ivFoodImgPreview.setImageURI(uri)
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
    private lateinit var currentLable: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNewFoodItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initConfig()
        //firebase config
        FirebaseApp.initializeApp(applicationContext)
        var db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        var dbFoodItemRef = db.getReference("foodItem")
        val sharedPreferences = getSharedPreferences("wavesoffood", MODE_PRIVATE)

        currentLable = binding.btnBreakfast
        currentLable.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))



        binding.btnLunch.setOnClickListener {
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            currentLable = binding.btnLunch

            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))

        }
        binding.btnBreakfast.setOnClickListener {
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            currentLable = binding.btnBreakfast
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))

        }
        binding.btnDinner.setOnClickListener {
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            currentLable = binding.btnDinner
            currentLable.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))

        }


        //loadImg
        binding.ivFoodImgPreview.setOnClickListener {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                // Android 14+ only
                pickImageLauncher.launch(
                    androidx.activity.result.PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            } else {
                Toast.makeText(this, "This feature requires Android 14+", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSave.setOnClickListener {
            var foodCat = currentLable.text.toString().trim()
            var foodName = binding.etFoodName.text.toString().trim()
            var foodPrice = binding.etFoodPrice.text.toString().trim()
            var foodDesc = binding.etFoodDesc.text.toString().trim()
            var resID = sharedPreferences.getString("resID", null)
            Toast.makeText(applicationContext, resID.toString(), Toast.LENGTH_SHORT).show()
            if (foodDesc.isEmpty() || foodPrice.isEmpty() || foodName.isEmpty() || foodCat.isEmpty()) {
                Toast.makeText(applicationContext, "All field are required", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (!::imageUri.isInitialized) {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val file = uriToFile(imageUri)
            MediaManager.get().upload(file.path).option("upload_preset", "wavesoffood_preset")
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String?) {
                        binding.sv.visibility = View.GONE
                        binding.successLayout.visibility = View.VISIBLE
                        binding.successLayout.findViewById<LinearLayout>(R.id.ll1).visibility =
                            View.GONE
                        binding.successLayout.findViewById<LinearLayout>(R.id.ll2).visibility =
                            View.VISIBLE

                        Toast.makeText(applicationContext, "started", Toast.LENGTH_SHORT).show()

                    }

                    override fun onProgress(
                        requestId: String?,
                        bytes: Long,
                        totalBytes: Long
                    ) {
                        Toast.makeText(applicationContext, "Uploading", Toast.LENGTH_SHORT).show()

                    }

                    override fun onSuccess(
                        requestId: String?,
                        resultData: Map<*, *>?
                    ) {

                        val url = resultData?.get("secure_url").toString()
                        var foodId = dbFoodItemRef.push().key
                        var foodItemModel = FoodItemModel(
                            foodId,
                            foodName,
                            foodCat,
                            foodPrice.toIntOrNull(),
                            url,
                            resID
                        )
//                   var myFoodItemRef = dbFoodItemRef.setValue(foodId)
                        dbFoodItemRef.child(foodId.toString()).setValue(foodItemModel)
                            .addOnSuccessListener {

                                Toast.makeText(
                                    applicationContext,
                                    "Item saved successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(
                                    applicationContext,
                                    "Failed to save item: ${exception.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Optional: You can log success or perform something after completion
                                } else {
                                    // Optional: Log the error
                                    val error = task.exception?.message
                                    Toast.makeText(
                                        applicationContext,
                                        "Something went wrong: $error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }



                        binding.successLayout.findViewById<LinearLayout>(R.id.ll2).visibility =
                            View.GONE
                        binding.successLayout.findViewById<LinearLayout>(R.id.ll1).visibility =
                            View.VISIBLE

                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(
                        requestId: String?,
                        error: ErrorInfo?
                    ) {
                        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT)
                            .show()

                    }

                    override fun onReschedule(
                        requestId: String?,
                        error: ErrorInfo?
                    ) {
                        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT)
                            .show()

                    }

                }).dispatch()

        }
        binding.successLayout.findViewById<Button>(R.id.btnOk).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()

        }

    }

    private fun uriToFile(uri: Uri): File {
        val inputStream = contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", ".jpg", cacheDir)
        tempFile.outputStream().use { output ->
            inputStream?.copyTo(output)
        }
        return tempFile
    }

    private fun initConfig() {
        var config = HashMap<String, String>()
        config.put("cloud_name", BuildConfig.cloudinary_cloud_name);
        config.put("api_key", BuildConfig.cloudinary_api_key)
        config.put("api_secret", BuildConfig.cloudinary_api_secret)
//        config.put("secure", true);
        MediaManager.init(this, config);
    }
}