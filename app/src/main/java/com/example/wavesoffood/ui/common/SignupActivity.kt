package com.example.wavesoffood.ui.common

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
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

class SignupActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        var name = ""
        var email = ""
        var pass = ""
        var cpass = ""
        var utype = ""
        var id = ""
        var isPassVisibile = false
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreferences = getSharedPreferences("wavesoffood", MODE_PRIVATE)
        FirebaseApp.initializeApp(applicationContext)
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val usersRef = db.getReference("users")


        val et_name = findViewById<EditText>(R.id.et_name)
        val et_email = findViewById<EditText>(R.id.et_email)
        val et_pass = findViewById<EditText>(R.id.et_pass)
        val et_cpass = findViewById<EditText>(R.id.et_cpass)
        val tv_login = findViewById<TextView>(R.id.tv_login)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        val btn_signup = findViewById<Button>(R.id.btn_signup)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            utype = selectedRadioButton.text.toString()
        }


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
                        et_pass.setCompoundDrawablesRelativeWithIntrinsicBounds(  drawableStart , null, newDrawable, null)

                        v.performClick() // For accessibility
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
        var iscPassVisible = false // Track password visibility

        et_cpass.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = et_cpass.compoundDrawablesRelative[2] // [2] = end
                val drawableStart = et_cpass.compoundDrawablesRelative[0]

                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchX = event.x

                    // Check if user tapped within the drawableEnd area
                    if (touchX >= (et_cpass.width - et_cpass.paddingEnd - drawableWidth)) {

                        // Toggle visibility state
                        iscPassVisible = !iscPassVisible

                        // Change input type
                        if (iscPassVisible) {
                            et_cpass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            et_cpass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        // Move cursor to end
                        et_cpass.setSelection(et_cpass.text.length)

                        // Change drawable icon
                        val drawableId = if (iscPassVisible) R.drawable.eye else R.drawable.eye_off
                        val newDrawable = ContextCompat.getDrawable(this, drawableId)
                        et_cpass.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart, null, newDrawable, null)

                        v.performClick() // For accessibility
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }













        btn_signup.setOnClickListener{

              name = et_name.text.toString().trim()
              email = et_email.text.toString().trim()
              pass = et_pass.text.toString().trim()
              cpass = et_cpass.text.toString().trim()
              id = email.replace(".","_")

            var selectedid = radioGroup.checkedRadioButtonId
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || cpass.isEmpty() || selectedid == -1){
                Toast.makeText(applicationContext,"all fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!pass.equals(cpass)){
                Toast.makeText(applicationContext,"Password Do Not Match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usermodel = UserModel(id,name,email,pass,"default.png",utype)




            val myUserRef = usersRef.child(id)
            myUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()){
                        Toast.makeText(applicationContext, "User Exist", Toast.LENGTH_SHORT).show()
                        return;
                    }else{
                        myUserRef.setValue(usermodel).addOnCompleteListener { task ->  // task is of type Task<Void>
                            if (task.isSuccessful) {
                                val editor = sharedPreferences.edit()
                                editor.putString("name",name)
                                editor.putString("email",email)
                                editor.putString("profile_img",usermodel.user_profile)
                                editor.putBoolean("IsLoggedIn",true)
                                editor.putString("uType",utype)
                                editor.apply()
                                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
                                if (utype.equals("User")){

                                    val intent = Intent(applicationContext, UserHome::class.java)
                                    startActivity(intent)
                                    finish()
                                }else if(utype.equals("Restaurant")){

                                    val intent = Intent(applicationContext, RestaurantHomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                            } else {
                                Toast.makeText(applicationContext, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()

                }
            })

        }
        tv_login.setOnClickListener{
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}