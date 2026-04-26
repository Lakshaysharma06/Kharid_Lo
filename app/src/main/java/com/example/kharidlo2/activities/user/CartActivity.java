package com.example.kharidlo2.activities.user;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.kharidlo2.R;
import com.kharidlo.adapters.CartAdapter;
import com.kharidlo.models.CartManager;

import java.util.ArrayList;

/**
 * CartActivity — displays all items in the cart with quantity controls,
 * a live order summary, and a "Proceed to Order" button.
 */
public class CartActivity extends AppCompatActivity {

    private TextView tvSubtotal, tvTotal;
    private LinearLayout llEmptyCart;
    private CardView cardSummary;
    private RecyclerView rvCartItems;
    private CartAdapter adapter;
    private final CartManager cart = CartManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvSubtotal   = findViewById(R.id.tvSubtotal);
        tvTotal      = findViewById(R.id.tvTotal);
        llEmptyCart  = findViewById(R.id.llEmptyCart);
        cardSummary  = findViewById(R.id.cardSummary);
        rvCartItems  = findViewById(R.id.rvCartItems);

        // Back
        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(v -> onBackPressed());

        // Clear cart
        TextView tvClear = findViewById(R.id.tvClearCart);
        tvClear.setOnClickListener(v -> {
            cart.clearCart();
            refreshUI();
            Toast.makeText(this, "Cart cleared", Toast.LENGTH_SHORT).show();
        });

        // Proceed button → Address screen
        Button btnProceed = findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(v -> {
            if (cart.getTotalItems() == 0) {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, AddressActivity.class));
            }
        });

        // RecyclerView
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(this,
                new ArrayList<>(cart.getCartItems()),   // snapshot of current cart
                this::refreshUI);
        rvCartItems.setAdapter(adapter);

        refreshUI();
        setupBottomNav();
    }

    /**
     * Refreshes totals, empty-state visibility, and adapter data.
     * Called after every cart mutation.
     */
    private void refreshUI() {
        boolean empty = cart.getTotalItems() == 0;

        llEmptyCart.setVisibility(empty ? View.VISIBLE : View.GONE);
        rvCartItems.setVisibility(empty ? View.GONE : View.VISIBLE);
        cardSummary.setVisibility(empty ? View.GONE : View.VISIBLE);

        if (!empty) {
            tvSubtotal.setText(cart.getTotalPriceFormatted());
            tvTotal.setText(cart.getTotalPriceFormatted());

            // Rebuild adapter list from the live CartManager data
            adapter = new CartAdapter(this,
                    new ArrayList<>(cart.getCartItems()),
                    this::refreshUI);
            rvCartItems.setAdapter(adapter);
        }
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_cart);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            } else if (id == R.id.nav_requests) {
                startActivity(new Intent(this, MyRequestsActivity.class));
                return true;
            }
            return true;
        });
    }
}
