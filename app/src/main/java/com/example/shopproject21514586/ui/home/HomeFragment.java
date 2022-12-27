package com.example.shopproject21514586.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.Product.CardViewAdapter;
import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.Product.ProductsAdapter;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.ui.category.Category;
import com.example.shopproject21514586.ui.category.CategoryAdapter;
import com.example.shopproject21514586.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private CategoryAdapter categoryAdapter;
    private RecyclerView productsRecycler;
    private List<Product> productList;
    private List<Category> categoryList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        categoryList = new ArrayList<>();
        productList = new ArrayList<>();
        LinearSnapHelper snapHelper = new LinearSnapHelper();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabase = database.getReference("Products");


        productsRecycler = view.findViewById(R.id.product_recycler);
        productsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Set up the query to search for products with a name that contains the search text
        SearchView searchView = view.findViewById(R.id.productSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                                    ||product.getCategory().contains(newText)
                                        ||product.getBrand().contains(newText)) {
                                productList.add(product);
                            }

                        }
                        // Pass the data to the CpusAdapter and set the adapter for the RecyclerView
                        CardViewAdapter adapter = new CardViewAdapter(productList);
                        adapter.setOnItemClickListener(new CardViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Product product) {
                                Bundle bundle = new Bundle();
                                bundle.putString("product", product.getName());
                                bundle.putString("price", product.getPrice());
                                bundle.putString("brand", product.getBrand());
                                bundle.putString("category", product.getCategory());
                                bundle.putString("description", product.getDescription());
                                bundle.putString("image", product.getImageUrl());
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

        // Get a reference to the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);
        // Set the layout manager for the recycler view
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager1);
        // Create a list of categoryList
        mDatabase.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.getKey();

                    categoryList.add(new Category(name, "", ""));
                }
                categoryAdapter = new CategoryAdapter(categoryList);
                categoryAdapter.setOnItemClickListener(category -> {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName", category.getName());
                Navigation.findNavController(view).navigate(R.id.action_navigate_to_category, bundle);
                });

                // Set the adapter for the recycler view
                snapHelper.attachToRecyclerView(recyclerView);
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(getContext(), R.layout.fragment_home);


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