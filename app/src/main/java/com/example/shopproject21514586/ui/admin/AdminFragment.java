package com.example.shopproject21514586.ui.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.UserActivities.MainActivity;
import com.example.shopproject21514586.databinding.FragmentAdminBinding;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class AdminFragment extends Fragment {

    private FragmentAdminBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    EditText name;
    EditText price;
    EditText description;
    EditText image;
    Spinner category;
    Button addProduct;
    EditText brand;
    Button addCategory;
    String IMGURL;
    DatabaseReference ref;
    FirebaseDatabase database;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        //Id's for the fields
        name = view.findViewById(R.id.nameOfProduct);
        price = view.findViewById(R.id.productPrice);
        description = view.findViewById(R.id.description);
        category = view.findViewById(R.id.category);
        image = view.findViewById(R.id.addImg);
        addProduct = view.findViewById(R.id.BtnAddProduct);
        brand = view.findViewById(R.id.brand);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        ref = database.getReference("Products");
        List<String> categories = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    categories.add(snapshot.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        // Set an OnItemSelectedListener for the spinner
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item
                String selected = adapterView.getItemAtPosition(i).toString();
                //Make the spinner show the selected item
                category.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                category.setSelection(0);
            }
        });

        //Add category
        //On click listener for the button
        addCategory = view.findViewById(R.id.btnCategory);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCategory("GPU", "Products", ref);
            }
        });
        openGallery();


        //On click listener for the add product button
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the product to the database with url to the image
                //Download url
                addNewProduct(ref, name.getText().toString(), description.getText().toString(), price.getText().toString(), IMGURL, category.getSelectedItem().toString());
                //Start main activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }

    public  void addNewCategory(String newCategory, String category, DatabaseReference ref){
        ref.child(newCategory).setValue("");
    }

    public void addNewProduct(DatabaseReference d, String name, String description, String price, String image, String category) {
        //Push a new product to the database
        d.child(category).child(name).setValue(new Product(name, price, description, image));
    }

    //Open up gallery
    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 1000);
        //Check if the user has selected an image
        if (gallery != null) {
            Toast.makeText(getContext(), "Image selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the request was successful
        if (requestCode == 1000  && data != null && data.getData() != null) {
            // Get the image URI
            Uri imageUri = data.getData();

            // Save to firebase
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference imagesRef = storageRef.child("images");
            StorageReference imageRef = imagesRef.child(imageUri.getLastPathSegment());
            imageRef.putFile(imageUri);
            //Call image from database
            IMGURL = imageUri.toString();

        }
    }
}