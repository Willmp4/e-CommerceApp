package com.example.shopproject21514586.ui.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.Product.CardViewAdapter;
import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.Product.ProductsAdapter;
import com.example.shopproject21514586.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends androidx.fragment.app.Fragment {
    private String category;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String categoryName = getArguments().getString("categoryName");

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

// Set the horizontal and vertical spacing between items
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        };

        spanSizeLookup.setSpanIndexCacheEnabled(true);
        layoutManager.setSpanSizeLookup(spanSizeLookup);


        View root = inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);


        // Set a GridLayoutManager for the RecyclerView
        recyclerView.setLayoutManager(layoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("Products");
        ref.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve data from the database
                List<Product> products = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product.getCategory().equals(categoryName)) {
                        products.add(product);
                    }
                }
                // Pass the data to the CpusAdapter and set the adapter for the RecyclerView
                CardViewAdapter adapter = new CardViewAdapter(products);
                adapter.setOnItemClickListener(product -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", product.getName());
                    bundle.putString("price", product.getPrice());
                    bundle.putString("description", product.getDescription());
                    bundle.putString("category", product.getCategory());
                    bundle.putString("brand", product.getBrand());
                    bundle.putString("image", product.getImageUrl());
                    Navigation.findNavController(root).navigate(R.id.action_navigate_to_product, bundle);

                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        updateUi(categoryName, root);

        return root;

    }

    public void updateUi(String title, View root) {
        TextView header = root.findViewById(R.id.categoryTitle);
        header.setText(title);
    }

}
