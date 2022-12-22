package com.example.shopproject21514586.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.R;
import com.example.shopproject21514586.ui.category.Category;
import com.example.shopproject21514586.ui.category.CategoryAdapter;
import com.example.shopproject21514586.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    CategoryAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        List<Category> categories = new ArrayList<>();
        LinearSnapHelper snapHelper = new LinearSnapHelper();


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabase = database.getReference("Products");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        // Get a reference to the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);

        // Set the layout manager for the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        // Create a list of categories
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.getKey();
                    categories.add(new Category(name, "", ""));
                }
                adapter = new CategoryAdapter(categories);
                adapter.setOnItemClickListener(category -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("categoryName", category.getName());
                    Navigation.findNavController(view).navigate(R.id.action_navigate_to_category, bundle);
                });


                snapHelper.attachToRecyclerView(recyclerView);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onStart(){
        super.onStart();

    }
}