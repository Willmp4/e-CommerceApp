package com.example.shopproject21514586.Product;

import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class ProductFragment extends Fragment {
    private Button addToBasketButton;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product, container, false);
        TextView productName = root.findViewById(R.id.item_name);
        TextView productPrice = root.findViewById(R.id.item_price);
        TextView productDescription = root.findViewById(R.id.item_description);
        ImageView productImage = root.findViewById(R.id.item_image);
        addToBasketButton = root.findViewById(R.id.BtnBasket);

        String name = getArguments().getString("name");
        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
        String price = getArguments().getString("price");
        String description = getArguments().getString("description");
        String image = getArguments().getString("image");

        productName.setText(name);
        productPrice.setText(price);
        productDescription.setText(description);
        Glide.with(productImage.getContext()).load(image).into(productImage);


        // Set a GridLayoutManager for the RecyclerView

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("Products");
        //On click
        addToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //go to basket
                NavController navController = Navigation.findNavController(getActivity(),
                        R.id.nav_host_fragment_content_main_navigation);
                navController.navigate(R.id.nav_shopping_basket);

            }
        });

        return root;

    }

    public void updateUi(String title, View root) {
        TextView header = root.findViewById(R.id.categoryTitle);
        header.setText(title);
    }

}
