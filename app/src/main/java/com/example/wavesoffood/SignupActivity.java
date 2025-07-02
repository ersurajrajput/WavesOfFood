package com.example.wavesoffood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {
    EditText name,email,pass;
    TextView tv_olduser;
    Button btn_google,btn_facebook,btn_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       name = findViewById(R.id.et_email);
       email = findViewById(R.id.et_email);
       pass = findViewById(R.id.et_pass);
       tv_olduser = findViewById(R.id.tv_newuser);
       btn_signup = findViewById(R.id.btn_Login);
       btn_facebook = findViewById(R.id.btn_facebook);
       btn_google = findViewById(R.id.btn_google);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().isEmpty()){

                    name.setBackground(ContextCompat.getDrawable(SignupActivity.this, R.drawable.error_et_bg));

                    return;
                }
                if (email.getText().toString().trim().isEmpty()){

                    email.setBackground(ContextCompat.getDrawable(SignupActivity.this, R.drawable.error_et_bg));

                    return;
                }
                if (pass.getText().toString().trim().isEmpty()){

                    pass.setBackground(ContextCompat.getDrawable(SignupActivity.this, R.drawable.error_et_bg));

                    return;
                }
                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(i);


            }
        });
        tv_olduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });




    }
}