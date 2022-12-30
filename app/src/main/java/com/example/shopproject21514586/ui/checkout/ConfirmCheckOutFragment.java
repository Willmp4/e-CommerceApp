package com.example.shopproject21514586.ui.checkout;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.Product.CardViewAdapter;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.basket.Basket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmCheckOutFragment extends Fragment {
    TextView address;
    TextView postalCode;
    TextView total;
    Button confirmButton;
    Button cancelButton;
    DatabaseReference ref;
    FirebaseDatabase database;
    Basket basket;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_checkout, container, false);

        address = view.findViewById(R.id.showAddress);
        postalCode = view.findViewById(R.id.showPostCode);
        total = view.findViewById(R.id.total);
        confirmButton = view.findViewById(R.id.orderBtn);
        cancelButton = view.findViewById(R.id.cancleBtn);
        recyclerView = view.findViewById(R.id.displayProducts);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        basket = Basket.getInstance();
        database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        ref.child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String addressValue = dataSnapshot.getValue(String.class);
                // Set the value of the address TextView to the value retrieved from the database
                address.setText(addressValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
                Log.e("Database Error", databaseError.getMessage());
            }
        });

        ref.child("postcode").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String postcodeValue = dataSnapshot.getValue(String.class);
                // Set the value of the postcode TextView to the value retrieved from the database
                postalCode.setText(postcodeValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
                Log.e("Database Error", databaseError.getMessage());
            }
        });

        total.setText("£" + String.valueOf(basket.getTotal()));

        //Set recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CardViewAdapter adapter =new CardViewAdapter(basket.getProducts(),R.layout.final_card,null);
        // Get the number of items in the adapter

        recyclerView.setAdapter(adapter);



        return view;
    }

}
