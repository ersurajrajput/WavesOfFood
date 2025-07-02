package com.example.wavesoffood;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseLocationActivity extends AppCompatActivity {
    AutoCompleteTextView citi_list;
    String[] locationlist = {
            "Delhi", "Mumbai", "Bengaluru", "Hyderabad", "Ahmedabad",
            "Chennai", "Kolkata", "Pune", "Jaipur", "Lucknow",
            "Kanpur", "Nagpur", "Indore", "Bhopal", "Patna",
            "Ludhiana", "Agra", "Nashik", "Vadodara", "Varanasi",
            "Amritsar", "Ranchi", "Guwahati", "Thiruvananthapuram", "Kozhikode",
            "Coimbatore", "Madurai", "Visakhapatnam", "Surat", "Rajkot",
            "Jodhpur", "Raipur", "Dehradun", "Noida", "Gurugram",
            "Chandigarh", "Jamshedpur", "Mysuru", "Udaipur", "Shimla"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        citi_list = findViewById(R.id.city_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                locationlist
        );
        citi_list.setAdapter(adapter);

    }
}