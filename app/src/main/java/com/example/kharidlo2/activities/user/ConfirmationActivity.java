package com.example.kharidlo2.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kharidlo2.R;

/**
 * ConfirmationActivity — success screen shown after a successful order.
 *
 * Receives:
 *   ORDER_ID (String) — the generated order reference number.
 *
 * Offers two actions:
 *   • Back to Home  → HomeActivity (clears back stack)
 *   • View Requests → MyRequestsActivity
 */
public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Display order ID
        String orderId = getIntent().getStringExtra("ORDER_ID");
        TextView tvOrderId = findViewById(R.id.tvOrderId);
        if (orderId != null) tvOrderId.setText(orderId);

        // Back to Home — clear the entire back stack so user can't go back to cart
        Button btnGoHome = findViewById(R.id.btnGoHome);
        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // View My Requests
        Button btnViewRequests = findViewById(R.id.btnViewRequests);
        btnViewRequests.setOnClickListener(v -> {
            startActivity(new Intent(this, MyRequestsActivity.class));
            finish();
        });
    }

    // Prevent accidental back navigation after a completed order
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

