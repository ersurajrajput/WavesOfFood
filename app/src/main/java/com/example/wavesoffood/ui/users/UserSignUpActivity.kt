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
import com.example.wavesoffood.Models.Usermodel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityUserLoginBinding
import com.example.wavesoffood.databinding.ActivityUserSignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class UserSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserSignUpBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        FirebaseApp.initializeApp(applicationContext)
        var db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        var dbUsersRef = db.getReference("users")
        val sharedPreferences = getSharedPreferences("wavesoffood", MODE_PRIVATE)

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
                            binding.etUserPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            binding.etUserPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        // Move cursor to end
                        binding.etUserPass.setSelection(binding.etUserPass.text.length)

                        // Change drawable icon
                        val drawableId = if (isPassVisible) R.drawable.icon_eye else R.drawable.icon_eye_off
                        val newDrawable = ContextCompat.getDrawable(this, drawableId)
                        binding.etUserPass.setCompoundDrawablesRelativeWithIntrinsicBounds(  drawableStart , null, newDrawable, null)

                        v.performClick() // For accessibility
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

         isPassVisible = false // Track password visibility
        binding.etCUserPass.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.etCUserPass.compoundDrawablesRelative[2] // [2] = end
                val drawableStart = binding.etCUserPass.compoundDrawablesRelative[0]

                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchX = event.x

                    // Check if user tapped within the drawableEnd area
                    if (touchX >= (binding.etCUserPass.width - binding.etCUserPass.paddingEnd - drawableWidth)) {

                        // Toggle visibility state
                        isPassVisible = !isPassVisible

                        // Change input type
                        if (isPassVisible) {
                            binding.etCUserPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            binding.etCUserPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        // Move cursor to end
                        binding.etCUserPass.setSelection(binding.etCUserPass.text.length)

                        // Change drawable icon
                        val drawableId = if (isPassVisible) R.drawable.icon_eye else R.drawable.icon_eye_off
                        val newDrawable = ContextCompat.getDrawable(this, drawableId)
                        binding.etCUserPass.setCompoundDrawablesRelativeWithIntrinsicBounds(  drawableStart , null, newDrawable, null)

                        v.performClick() // For accessibility
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

        binding.tvLogin.setOnClickListener {
            var intent  = Intent(applicationContext, UserLoginActivity::class.java)
            startActivity(intent)
        }
        binding.ivGoogle.setOnClickListener {
            Toast.makeText(applicationContext,"Comming Soong", Toast.LENGTH_SHORT).show()
        }
        binding.ivFacebook.setOnClickListener {
            Toast.makeText(applicationContext,"Comming Soong", Toast.LENGTH_SHORT).show()
        }
        binding.ivTwitter.setOnClickListener {
            Toast.makeText(applicationContext,"Comming Soong", Toast.LENGTH_SHORT).show()
        }

        //singnup logic
        binding.btnSignUp.setOnClickListener {
            var userName = binding.etUserName.text.toString().trim()
            var userEmail = binding.etUserEmail.text.toString().trim()
            var userPass = binding.etUserPass.text.toString().trim()
            var userCPass = binding.etCUserPass.text.toString().trim()
            var defaultUserImg = ""
          if (userName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty() || userCPass.isEmpty()){
              Toast.makeText(this,"All Field Are Required", Toast.LENGTH_SHORT).show()
              return@setOnClickListener
          }
            if (!userCPass.equals(userPass)){
                Toast.makeText(this,"Password do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var emailKey = userEmail.replace(".","_")
            var userModel = Usermodel(emailKey,userName,userEmail,userPass,defaultUserImg)
            var myUserRef = dbUsersRef.child(emailKey)
            myUserRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        Toast.makeText(applicationContext,"User Exist", Toast.LENGTH_SHORT).show()
                        return

                    }else{
                        myUserRef.setValue(userModel).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                var editor = sharedPreferences.edit()
                                editor.putBoolean("isLoggedIn",true)
                                editor.putString("userName",userModel.userName)
                                editor.putString("userID",userModel.id)
                                editor.putString("userEmail",userModel.userEmail)
                                editor.putString("userImg",userModel.userImg)
                                editor.putString("uType","user")
                                editor.apply()
                                Toast.makeText(applicationContext,"Saved", Toast.LENGTH_SHORT).show()
                                var intent = Intent(applicationContext, UserHomeActivity::class.java)
                                startActivity(intent)
                                finish()

                            }else{
                                Toast.makeText(applicationContext, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()

                }

            })
        }



    }
}