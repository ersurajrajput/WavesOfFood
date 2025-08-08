package com.example.wavesoffood.ui.res

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.Helpers.LoginChakerHelper
import com.example.wavesoffood.Helpers.SharedPrefHelper
import com.example.wavesoffood.Models.ResModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityResLoginBinding
import com.example.wavesoffood.databinding.ActivityUserLoginBinding
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ResLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResLoginBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        FirebaseApp.initializeApp(applicationContext)
        var db = Firebase.database(com.example.wavesoffood.BuildConfig.FIREBASE_DB_URL)
        var dbResRef = db.getReference("res")

        //login checker
        var loginChakerHelper = LoginChakerHelper(applicationContext)
        if (loginChakerHelper.isLoggedIn()) {
            loginChakerHelper.loginRouteHelper()
        }


        binding.btnLogin.setOnClickListener {
            var email = binding.etResEmail.text.toString().trim()
            var pass = binding.etResPass.text.toString().trim()
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(applicationContext, "All Field Are Required", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            var id = email.replace(".", "_")
            dbResRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var resModel = snapshot.getValue(ResModel::class.java)
                        if (resModel != null && resModel.resPass.equals(pass)) {
                            var sharedPrefHelper = SharedPrefHelper(applicationContext)
                            sharedPrefHelper.saveRes(resModel)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Wrong Password", Toast.LENGTH_SHORT)
                                .show()

                        }
                    } else {
                        Toast.makeText(applicationContext, "Wrong Email", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()

                }

            })

        }
        binding.tvSingup.setOnClickListener {
            var intent = Intent(applicationContext, ResSignUpActivity::class.java)
            startActivity(intent)
        }


        var isPassVisible = false // Track password visibility
        binding.etResPass.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.etResPass.compoundDrawablesRelative[2] // [2] = end
                val drawableStart = binding.etResPass.compoundDrawablesRelative[0]

                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchX = event.x

                    // Check if Res tapped within the drawableEnd area
                    if (touchX >= (binding.etResPass.width - binding.etResPass.paddingEnd - drawableWidth)) {

                        // Toggle visibility state
                        isPassVisible = !isPassVisible

                        // Change input type
                        if (isPassVisible) {
                            binding.etResPass.inputType =
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            binding.etResPass.inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        // Move cursor to end
                        binding.etResPass.setSelection(binding.etResPass.text.length)

                        // Change drawable icon
                        val drawableId =
                            if (isPassVisible) R.drawable.icon_eye else R.drawable.icon_eye_off
                        val newDrawable = ContextCompat.getDrawable(this, drawableId)
                        binding.etResPass.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            drawableStart,
                            null,
                            newDrawable,
                            null
                        )

                        v.performClick() // For accessibility
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
        binding.ivGoogle.setOnClickListener {
            Toast.makeText(applicationContext, "Comming Soong", Toast.LENGTH_SHORT).show()
        }
        binding.ivFacebook.setOnClickListener {
            Toast.makeText(applicationContext, "Comming Soong", Toast.LENGTH_SHORT).show()
        }
        binding.ivTwitter.setOnClickListener {
            Toast.makeText(applicationContext, "Comming Soong", Toast.LENGTH_SHORT).show()
        }
    }
}