package com.example.shopproject21514586.ui.home.categories.cpus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.Product.ProductsAdapter;
import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.databinding.FragmentCpusBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CpusFragment extends Fragment {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private AppBarConfiguration mAppBarConfiguration;
    private FragmentCpusBinding binding;

    Button nav_home;

   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_cpus, container, false);
       RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
       LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
       recyclerView.setLayoutManager(layoutManager);
       FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
       DatabaseReference ref = database.getReference("Products").child("CPU");
       ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // Retrieve data from the database
               List<Product> products = new ArrayList<>();
               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Product product = snapshot.getValue(Product.class);
                   products.add(product);
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
       return root;

   }
}
