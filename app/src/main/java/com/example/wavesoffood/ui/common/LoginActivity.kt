package com.example.wavesoffood.ui.common

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.R
import com.example.wavesoffood.model.UserModel
import com.example.wavesoffood.ui.restaurant.RestaurantHomeActivity
import com.example.wavesoffood.ui.user.UserHome
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ///firebase
        val sharedPreferences = getSharedPreferences("my_app_preferences", MODE_PRIVATE)
        FirebaseApp.initializeApp(applicationContext)
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val usersRef = db.getReference("users")

        val et_email = findViewById<EditText>(R.id.et_email)
        val et_pass = findViewById<EditText>(R.id.et_pass)
        val tv_forgetPass = findViewById<TextView>(R.id.tv_forgetPass)
        val tv_singup = findViewById<TextView>(R.id.tv_singup)
        val btn_login = findViewById<Button>(R.id.btn_login)





        var isPassVisible = false // Track password visibility

        et_pass.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = et_pass.compoundDrawablesRelative[2] // [2] = end
                val drawableStart = et_pass.compoundDrawablesRelative[0]

                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchX = event.x

                    // Check if user tapped within the drawableEnd area
                    if (touchX >= (et_pass.width - et_pass.paddingEnd - drawableWidth)) {

                        // Toggle visibility state
                        isPassVisible = !isPassVisible

                        // Change input type
                        if (isPassVisible) {
                            et_pass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            et_pass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        // Move cursor to end
                        et_pass.setSelection(et_pass.text.length)

                        // Change drawable icon
                        val drawableId = if (isPassVisible) R.drawable.eye else R.drawable.eye_off
                        val newDrawable = ContextCompat.getDrawable(this, drawableId)
                        et_pass.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart, null, newDrawable, null)

                        v.performClick() // For accessibility
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }











        btn_login.setOnClickListener{
            val email = et_email.text.toString().trim()
            val pass = et_pass.text.toString().trim()
            if (email.isEmpty()){
                et_email.error = "require"
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                et_pass.error = "required"
                return@setOnClickListener
            }

            var id = email.replace(".","_")
            var myUserRef = usersRef.child(id)
            myUserRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var userModel = snapshot.getValue(UserModel::class.java)
                        if (userModel?.pass.equals(pass)){
                            var editor = sharedPreferences.edit()
                            editor.putString("name",userModel?.name)
                            editor.putString("email",userModel?.email)
                            editor.putString("id",userModel?.id)
                            editor.putString("user_type",userModel?.user_type)
                            editor.putString("user_profile",userModel?.user_profile)
                            editor.putBoolean("IsLoggedIn",true)
                            editor.apply()
                            if (userModel?.user_type.equals("User")){
                                var intent = Intent(applicationContext, UserHome::class.java)
                                startActivity(intent)
                                finish()
                            }else if(userModel?.user_type.equals("Restaurant")){
                                var intent = Intent(applicationContext, RestaurantHomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            Toast.makeText(applicationContext,"Login Succes", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(applicationContext,"wrong password", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(applicationContext,"Wrong Email", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext,error.message, Toast.LENGTH_SHORT).show()
                }

            })


        }










        tv_singup.setOnClickListener{
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
        }
        tv_forgetPass.setOnClickListener{
            val intent = Intent(applicationContext, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }




    }
}