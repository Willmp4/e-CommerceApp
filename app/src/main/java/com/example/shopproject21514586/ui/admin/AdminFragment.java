package com.example.shopproject21514586.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.databinding.FragmentAdminBinding;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminFragment extends Fragment {

    private FragmentAdminBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    EditText name;
    EditText price;
    EditText description;
    EditText image;
    EditText category;
    Button addProduct;
    EditText brand;
    Button addCategory;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        binding = FragmentAdminBinding.inflate(inflater, container, false);
        mDatabase = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        //Id's for the fields
        name = view.findViewById(R.id.nameOfProduct);
        price = view.findViewById(R.id.productPrice);
        description = view.findViewById(R.id.description);
        category = view.findViewById(R.id.category);
        image = view.findViewById(R.id.addImg);
        addProduct = view.findViewById(R.id.BtnAddProduct);
        brand = view.findViewById(R.id.brand);

        //On click listener for the add product button
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfProduct = name.getText().toString();
                String priceOfProduct = price.getText().toString();
                String descriptionOfProduct = description.getText().toString();
                String categoryOfProduct = category.getText().toString();
                String imageOfProduct = image.getText().toString();
                String brandOfProduct = brand.getText().toString();

                //If the fields are empty, the user will be notified
                if (nameOfProduct.isEmpty() || priceOfProduct.isEmpty() || descriptionOfProduct.isEmpty() || categoryOfProduct.isEmpty() || imageOfProduct.isEmpty() || brandOfProduct.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    //If the fields are not empty, the product will be added to the database
                    addNewProduct(nameOfProduct, descriptionOfProduct, priceOfProduct, imageOfProduct, categoryOfProduct);
                }
            }
        });


        return view;

    }

    public  void addNewCategory(String category){
        mDatabase.child("Products").child("Categories").child(category).setValue(category);
    }

    public void addNewProduct(String name, String description, String price, String image, String category) {
        //Push a new product to the database
        mDatabase.child("Products").child(category).child(name).setValue(new Product(name, price, description, image));

    }
}