package com.example.wavesoffood.ui.res

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityAddNewFoodItemBinding
import com.example.wavesoffood.databinding.ActivityResLoginBinding
import java.io.File

class AddNewFoodItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewFoodItemBinding
    private lateinit var imageUri: Uri
    private val pickImageLauncher  = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri : Uri ? ->
        if (uri != null){
            imageUri = uri
            binding.ivFoodImgPreview.setImageURI(uri)
        }else{
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }







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

        //loadImg
        binding.ivFoodImgPreview.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                // Android 14+ only
                pickImageLauncher.launch(androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                Toast.makeText(this, "This feature requires Android 14+", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnSave.setOnClickListener {
            Toast.makeText(applicationContext,"btn clicked", Toast.LENGTH_SHORT).show()
            if (!::imageUri.isInitialized) {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val file = uriToFile(imageUri)
            MediaManager.get().upload(file.path).option("upload_preset","wavesoffood_preset").callback(object : UploadCallback{
                override fun onStart(requestId: String?) {
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
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                    val url = resultData?.get("secure_url").toString()

                    Toast.makeText(applicationContext, "Uploaded!\n$url", Toast.LENGTH_LONG).show()

                }

                override fun onError(
                    requestId: String?,
                    error: ErrorInfo?
                ) {
                    Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()

                }

                override fun onReschedule(
                    requestId: String?,
                    error: ErrorInfo?
                ) {
                    Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()

                }

            }).dispatch()

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