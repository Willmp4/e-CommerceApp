package com.example.shopproject21514586.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.UserActivities.Category;
import com.example.shopproject21514586.UserActivities.CategoryAdapter;
import com.example.shopproject21514586.databinding.FragmentHomeBinding;
import com.google.android.material.navigation.NavigationView;
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
                    // Navigate to the category fragment
//                    Bundle bundle = new Bundle();
//                    bundle.putString("nav_", category.getName());
                    Navigation.findNavController(view).navigate(R.id.nav_cpus);
                });

                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //On click listener for the sign up button
//        Button cpuButton = view.findViewById(R.id.BtnCpu);
//        cpuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController navController = Navigation.findNavController(getActivity(),
//                        R.id.nav_host_fragment_content_main_navigation);
//                navController.navigate(R.id.nav_cpus);
//                //Change title of the action bar to the name of the fragment
//                getActivity().setTitle("Cpus");
//            }
//        });
//
//        Button GpuButton = view.findViewById(R.id.BtnGpu);
//        GpuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController navController = Navigation.findNavController(getActivity(),
//                        R.id.nav_host_fragment_content_main_navigation);
//                navController.navigate(R.id.nav_gpu);
//                //Change title of the action bar to the name of the fragment
//                getActivity().setTitle("Cpus");
//            }
//        });
//

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