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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        String categoryName = getArguments().getString("categoryName");


        View root = inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        //Change the category label to the category name from the database

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("Products");
        ref.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", "CHANGE: " + dataSnapshot.getKey());

                // Retrieve data from the database
                List<Product> products = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("TAG", "onDataChange: " + snapshot.getKey());
                    Product product = snapshot.getValue(Product.class);
                    if (product.getCategory().equals(categoryName)) {
                        products.add(product);
                    }

                }
                // Pass the data to the CpusAdapter and set the adapter for the RecyclerView
                ProductsAdapter adapter = new ProductsAdapter(products);
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
