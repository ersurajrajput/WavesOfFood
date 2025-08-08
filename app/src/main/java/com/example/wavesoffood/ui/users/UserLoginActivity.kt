package com.example.wavesoffood.ui.users

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
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.Helpers.LoginChakerHelper
import com.example.wavesoffood.Helpers.SharedPrefHelper
import com.example.wavesoffood.Models.Usermodel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityOnBoardingBinding
import com.example.wavesoffood.databinding.ActivityUserLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserLoginBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //firebase
        FirebaseApp.initializeApp(applicationContext)
        var db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        var dbUsersRef = db.getReference("users")

        //login checker
        var loginChakerHelper = LoginChakerHelper(applicationContext)
        if (loginChakerHelper.isLoggedIn()) {
            loginChakerHelper.loginRouteHelper()
        }


        var isPassVisible = false // Track password visibility
        binding.etUserPass.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.etUserPass.compoundDrawablesRelative[2] // [2] = end
                val drawableStart = binding.etUserPass.compoundDrawablesRelative[0]

                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchX = event.x

                    // Check if user tapped within the drawableEnd area
                    if (touchX >= (binding.etUserPass.width - binding.etUserPass.paddingEnd - drawableWidth)) {

                        // Toggle visibility state
                        isPassVisible = !isPassVisible

                        // Change input type
                        if (isPassVisible) {
                            binding.etUserPass.inputType =
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            binding.etUserPass.inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        // Move cursor to end
                        binding.etUserPass.setSelection(binding.etUserPass.text.length)

                        // Change drawable icon
                        val drawableId =
                            if (isPassVisible) R.drawable.icon_eye else R.drawable.icon_eye_off
                        val newDrawable = ContextCompat.getDrawable(this, drawableId)
                        binding.etUserPass.setCompoundDrawablesRelativeWithIntrinsicBounds(
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




        binding.btnLogin.setOnClickListener {
            var useEmail = binding.etUserEmail.text.toString().trim()
            var userPass = binding.etUserPass.text.toString().trim()
            if (userPass.isEmpty() || useEmail.isEmpty()) {
                Toast.makeText(applicationContext, "All Fields Are Required", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            var id = useEmail.replace(".", "_")
            dbUsersRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var userModel = snapshot.getValue(Usermodel::class.java)
                    if (snapshot.exists()) {
                        if (userModel?.userPass.equals(userPass) && userModel != null) {
                            var sharedPrefHelper = SharedPrefHelper(applicationContext)
                            sharedPrefHelper.saveUser(userModel)
                            Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
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

        binding.tvSignUp.setOnClickListener {
            var intent = Intent(applicationContext, UserSignUpActivity::class.java)
            startActivity(intent)
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