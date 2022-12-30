package com.example.shopproject21514586.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.Product.CardViewAdapter;
import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference mDatabase;
    private RecyclerView productsRecycler;
    Button goToSearch;
    Spinner spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabase = database.getReference("Products");

        goToSearch = view.findViewById(R.id.goToSearch);
        goToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigate_to_search);
            }
        });

        productsRecycler = view.findViewById(R.id.product_recycler);
        //Set Grid Layout
        productsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));


        // Get a reference to the recycler view
        spinner = view.findViewById(R.id.spinner);
        List<String> categories = new ArrayList<>();

        // Set the layout manager for the recycler view
        // Create a list of categoryList
        mDatabase.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    categories.add(snapshot.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String category = adapterView.getItemAtPosition(i).toString();
                        Query query = mDatabase.child("items").orderByChild("category").equalTo(category);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<Product> products = new ArrayList<>();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Product product = snapshot.getValue(Product.class);
                                    products.add(product);
                                }
                                CardViewAdapter adapter = new CardViewAdapter(products, R.layout.card_view, null);
                                adapter.setOnItemClickListener(new CardViewAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Product product) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("name", product.getName());
                                        bundle.putInt("price", product.getPrice());
                                        bundle.putString("description", product.getDescription());
                                        bundle.putString("image", product.getImageUrl());
                                        bundle.putString("category", product.getCategory());
                                        bundle.putString("id", product.getId());
                                        bundle.putString("brand", product.getBrand());
                                        bundle.putInt("quantity", product.getQuantity());
                                        Navigation.findNavController(view).navigate(R.id.action_navigate_to_product, bundle);
                                    }
                                });
                                productsRecycler.setAdapter(adapter);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //Show all products
                        Query query = mDatabase.child("Items").orderByChild("name");
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<Product> products = new ArrayList<>();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Product product = snapshot.getValue(Product.class);
                                    products.add(product);
                                }
                                CardViewAdapter adapter = new CardViewAdapter(products, R.layout.card_view, null);
                                productsRecycler.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//
//    public void onStart() {
//        super.onStart();
//
//    }

    private void searchProducts(String searchText, Query query  ) {
        // Get a reference to the "Category" node in the "Products" node
        // Attach a listener to the query to receive the search results


    }

//
//    private void updateUI() {
//
//            // Display the search results
////            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        productsRecycler.setLayoutManager(layoutManager1);
//        ProductsAdapter adapter = new ProductsAdapter(productList);
//        productsRecycler.setAdapter(adapter);
//
//    }
}