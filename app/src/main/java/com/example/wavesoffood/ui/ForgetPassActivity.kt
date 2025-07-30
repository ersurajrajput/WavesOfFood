package com.example.wavesoffood.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.R

class ForgetPassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forget_pass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btn_sendcode = findViewById<Button>(R.id.btn_sendcode);
        val rl1 = findViewById<RelativeLayout>(R.id.rl1);
        val rl2 = findViewById<RelativeLayout>(R.id.rl2);
        val btn_back = findViewById<ImageButton>(R.id.btn_back)


        btn_back.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        btn_sendcode.setOnClickListener{
            rl1.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    rl1.visibility = View.GONE
                    rl2.alpha = 0f
                    rl2.visibility = View.VISIBLE
                    rl2.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .start()
                }
                .start()

            

        }
    }
}