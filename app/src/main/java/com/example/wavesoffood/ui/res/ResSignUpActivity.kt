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
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivityResLoginBinding
import com.example.wavesoffood.databinding.ActivityResSignUpBinding

class ResSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResSignUpBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.tvLogin.setOnClickListener {
            var intent = Intent(applicationContext, ResLoginActivity::class.java)
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
                            binding.etResPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            binding.etResPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        // Move cursor to end
                        binding.etResPass.setSelection(binding.etResPass.text.length)

                        // Change drawable icon
                        val drawableId = if (isPassVisible) R.drawable.icon_eye else R.drawable.icon_eye_off
                        val newDrawable = ContextCompat.getDrawable(this, drawableId)
                        binding.etResPass.setCompoundDrawablesRelativeWithIntrinsicBounds(  drawableStart , null, newDrawable, null)

                        v.performClick() // For accessibility
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

        isPassVisible = false // Track password visibility
        binding.etCResPass.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.etCResPass.compoundDrawablesRelative[2] // [2] = end
                val drawableStart = binding.etCResPass.compoundDrawablesRelative[0]

                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchX = event.x

                    // Check if Res tapped within the drawableEnd area
                    if (touchX >= (binding.etCResPass.width - binding.etCResPass.paddingEnd - drawableWidth)) {

                        // Toggle visibility state
                        isPassVisible = !isPassVisible

                        // Change input type
                        if (isPassVisible) {
                            binding.etCResPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            binding.etCResPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        // Move cursor to end
                        binding.etCResPass.setSelection(binding.etCResPass.text.length)

                        // Change drawable icon
                        val drawableId = if (isPassVisible) R.drawable.icon_eye else R.drawable.icon_eye_off
                        val newDrawable = ContextCompat.getDrawable(this, drawableId)
                        binding.etCResPass.setCompoundDrawablesRelativeWithIntrinsicBounds(  drawableStart , null, newDrawable, null)

                        v.performClick() // For accessibility
                        return@setOnTouchListener true
                    }
                }
            }
            false
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
    }
}