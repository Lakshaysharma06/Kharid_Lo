package com.kharidlo.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kharidlo.R;
import com.kharidlo.activities.owner.OwnerLoginActivity;

/**
 * WelcomeActivity — first screen the user sees.
 * Offers two paths: Customer → LoginActivity, Owner → OwnerLoginActivity.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnCustomer = findViewById(R.id.btnCustomer);
        Button btnOwner    = findViewById(R.id.btnOwner);

        // Customer path
        btnCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        // Owner path
        btnOwner.setOnClickListener(v -> {
            Intent intent = new Intent(this, OwnerLoginActivity.class);
            startActivity(intent);
        });
    }
}
