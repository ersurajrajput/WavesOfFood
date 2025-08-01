package com.example.wavesoffood.ui.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.BuildConfig
import com.example.wavesoffood.R
import com.example.wavesoffood.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreferences = getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE)
        FirebaseApp.initializeApp(applicationContext)
        val db = Firebase.database(BuildConfig.FIREBASE_DB_URL)
        val myref = db.getReference("users")

        val et_name = findViewById<EditText>(R.id.et_name)
        val et_email = findViewById<EditText>(R.id.et_email)
        val et_pass = findViewById<EditText>(R.id.et_pass)
        val et_cpass = findViewById<EditText>(R.id.et_cpass)
        val tv_login = findViewById<TextView>(R.id.tv_login)

        val btn_signup = findViewById<Button>(R.id.btn_signup)

        btn_signup.setOnClickListener{
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val pass = et_pass.text.toString().trim()
            val cpass = et_cpass.text.toString().trim()
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || cpass.isEmpty()){
                Toast.makeText(applicationContext,"all fields are required",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!pass.equals(cpass)){
                Toast.makeText(applicationContext,"Password Do Not Match",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

           val usermodel = UserModel()
            usermodel.name = name
            usermodel.email = email
            usermodel.pass = pass
            usermodel.user_profile = "default.png"

            val emailkey = email.replace(".","_")
            val myuserRef = myref.child(emailkey)
            myuserRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()){
                        Toast.makeText(applicationContext, "User Exist", Toast.LENGTH_SHORT).show()
                        return;
                    }else{
                        myuserRef.setValue(usermodel).addOnCompleteListener { task ->  // task is of type Task<Void>
                            if (task.isSuccessful) {
                                val editor = sharedPreferences.edit()
                                editor.putString("name",name)
                                editor.putString("email",email)
                                editor.putString("profile_img",usermodel.user_profile)
                                editor.putBoolean("IsLoggedIn",true)
                                editor.apply()
                                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext,MainActivity::class.java)
                                startActivity(intent)
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
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}