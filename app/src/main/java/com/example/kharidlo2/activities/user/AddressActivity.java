package com.example.kharidlo2.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.example.kharidlo2.R;

/**
 * AddressActivity — collects room number, hostel block, and phone number
 * before proceeding to payment.
 */
public class AddressActivity extends AppCompatActivity {

    private TextInputEditText etRoom, etBlock, etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        etRoom  = findViewById(R.id.etRoom);
        etBlock = findViewById(R.id.etBlock);
        etPhone = findViewById(R.id.etPhone);

        // Back
        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(v -> onBackPressed());

        // Continue to payment
        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            String room  = getText(etRoom);
            String block = getText(etBlock);
            String phone = getText(etPhone);

            if (TextUtils.isEmpty(room)) {
                etRoom.setError("Enter your room number");
                return;
            }
            if (TextUtils.isEmpty(block)) {
                etBlock.setError("Enter hostel block / floor");
                return;
            }
            if (TextUtils.isEmpty(phone) || phone.length() < 10) {
                etPhone.setError("Enter a valid 10-digit phone number");
                return;
            }

            // Pass address details to PaymentActivity
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("ROOM",  room);
            intent.putExtra("BLOCK", block);
            intent.putExtra("PHONE", phone);
            startActivity(intent);
        });
    }

    private String getText(TextInputEditText f) {
        return f.getText() != null ? f.getText().toString().trim() : "";
    }
}
