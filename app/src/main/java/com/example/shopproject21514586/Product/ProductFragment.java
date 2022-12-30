package com.example.shopproject21514586.Product;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.shopproject21514586.UserActivities.MainActivity;
import com.example.shopproject21514586.basket.Basket;
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


        //Change the title of the action bar
        TextView productPrice = root.findViewById(R.id.item_price);
        TextView productDescription = root.findViewById(R.id.item_description);
        ImageView productImage = root.findViewById(R.id.item_image);
        addToBasketButton = root.findViewById(R.id.BtnBasket);


        Basket basket = Basket.getInstance();

        String name = getArguments().getString("name");


        ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get the product from the arguments
        int price = getArguments().getInt("price");
        String description = getArguments().getString("description");
        String image = getArguments().getString("image");
        String id = getArguments().getString("id");
        String category = getArguments().getString("category");
        int quantity = getArguments().getInt("quantity");
        String brand = getArguments().getString("brand");
        productPrice.setText("£" + String.valueOf(price));


        productDescription.setText(description);
        Glide.with(productImage.getContext()).load(image).into(productImage);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("Products");
        //On click
        addToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add product to basket
                Product product = new Product(name, price, description, image, category, brand, quantity, id);
                //Check if the product is already in the basket
                if (basket.contains(product)) {
                    Toast.makeText(getContext(), "Product already in basket", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Product added to basket", Toast.LENGTH_SHORT).show();
                    product.setCount(1);
                    basket.addProduct(product);

                }
            }
        });

        return root;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button press
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
