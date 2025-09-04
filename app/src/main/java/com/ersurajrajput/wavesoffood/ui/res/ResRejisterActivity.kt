package com.ersurajrajput.wavesoffood.ui.res

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.ActivityResRejisterBinding
import com.ersurajrajput.wavesoffood.models.ResModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResRejisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResRejisterBinding
    private var ResModel = ResModel()
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResRejisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // init
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()



        binding.btnGoogle.setOnClickListener {
            var googleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                    getString(R.string.default_web_client_id)
                ).requestEmail().build()
            googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
             
            GoogleSignInHeper()
        }
        binding.btnRegister.setOnClickListener {
            if (!inputChacker()) {
                return@setOnClickListener
            }
            emailPassRegister()
        }


    }

    private fun GoogleSignInHeper() {

        var signinIntent = googleSignInClient.signInIntent
        googleSignInClient.signOut()
        launcher.launch(signinIntent)


    }

    private fun saveResToDB(resModel: ResModel, uid: String) {
        Log.d("myTag", resModel.toString())
        var redDbRef = database.getReference("res")
        redDbRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    redDbRef.child(uid).setValue(resModel).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            var sharedPreferences = getSharedPreferences("WavesOfFood", Context.MODE_PRIVATE)
                            var editor = sharedPreferences.edit()
                            editor.putString("type","res")
                            editor.apply()
                            updateUI()

                        } else {
                            showError(task.exception?.message.toString())
                        }
 
                    }
                } else {
                    Toast.makeText(
                        this@ResRejisterActivity,
                        "Res Already Exist",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showError(error.message)
            }

        })

    }

    private fun updateUI() {
        var intent = Intent(this@ResRejisterActivity, ResMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(error: String) {
        AlertDialog.Builder(this@ResRejisterActivity).setMessage(error)
            .setPositiveButton("Retry") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            var resModel = ResModel()
                            resModel.resID = auth.currentUser?.uid.toString()
                            resModel.resName = auth.currentUser?.displayName.toString()
                            resModel.resEmail = auth.currentUser?.email.toString()
                            resModel.resMobile = auth.currentUser?.phoneNumber.toString()
                            resModel.resImg = auth.currentUser?.photoUrl.toString()
                            saveResToDB(resModel, auth.currentUser?.uid.toString())

                            Toast.makeText(
                                this@ResRejisterActivity,
                                "Regiter Succesfull",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            showError(task2.exception?.message.toString())
                        }

                    }

                } else {
                    showError(task.exception?.message.toString())
                      
                }
            }
        }

    private fun inputChacker(): Boolean {
        var resEmail = binding.etEmail.text.toString().trim()
        var resPass = binding.etPass.text.toString().trim()
        var resName = binding.etResName.text.toString().trim()
        var userName = binding.etUserName.text.toString().trim()


        if (resEmail.isEmpty() || resPass.isEmpty() || resName.isEmpty() || userName.isEmpty()) {
            showError("All Fileds Are Required")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(resEmail).matches()) {
            showError("Enter A Valid Email Address")
            return false
        }
        if (resPass.length < 6) {
            showError("Password Must Be 6 Character Long")
            return false
        }
        ResModel =
            ResModel(resEmail = resEmail, resPass = resPass, resName = resName, userName = userName)
        return true

    }

    private fun emailPassRegister() {
        auth.createUserWithEmailAndPassword(ResModel.resEmail.toString(), ResModel.resPass.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var uid = auth.currentUser?.uid
                    saveResToDB(ResModel, uid.toString())
                } else {
                    showError(task.exception?.message.toString())
                }
            }
    }

}