package com.example.shopproject21514586.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.Product.CardViewAdapter;
import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private DatabaseReference mDatabase;
    FirebaseDatabase database;
    List<Product> productList;
    RecyclerView productsRecycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Set up the query to search for products with a name that contains the search text

        productList = new ArrayList<>();
        LinearSnapHelper snapHelper = new LinearSnapHelper();

        database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabase = database.getReference("Products");


        productsRecycler = view.findViewById(R.id.product_recycler);
        //Set Grid Layout
        productsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        SearchView searchView = view.findViewById(R.id.productSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Called when the user submits the query. This could be due to a key press on the keyboard or due to pressing a submit button.
            @Override
            public boolean onQueryTextSubmit(String newText) {
                Query query = mDatabase.child("items").orderByChild("name").startAt(newText).endAt(newText + "\uf8ff");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);

                            if (product.getName().contains(newText)
                                    || product.getCategory().contains(newText)
                                    || product.getBrand().contains(newText)) {
                                Toast.makeText(getContext(), product.getCategory(), Toast.LENGTH_SHORT).show();
                                productList.add(product);
                            }

                        }
                        // Pass the data to the CpusAdapter and set the adapter for the RecyclerView
                        CardViewAdapter adapter = new CardViewAdapter(productList, R.id.checkout_card_view, null);
                        adapter.setOnItemClickListener(new CardViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Product product) {
                                Bundle bundle = new Bundle();
                                bundle.putString("product", product.getName());
                                bundle.putInt("price", product.getPrice());
                                bundle.putString("brand", product.getBrand());
                                bundle.putString("category", product.getCategory());
                                bundle.putString("description", product.getDescription());
                                bundle.putString("image", product.getImageUrl());
                                bundle.putInt("quantity", product.getQuantity());
                                //Start new intent to go to category activity
                                Navigation.findNavController(view).navigate(R.id.action_navigate_to_product, bundle);

                            }
                        });
                        productsRecycler.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("HomeFragment", "onCancelled", error.toException());
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return view;
    }
}
