package com.example.shopproject21514586.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopproject21514586.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckOutFragment extends Fragment {
    EditText address;
    EditText postcode;
    EditText city;
    EditText phone;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseDatabase database;
    Button confirm;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabase = database.getReference().child("Users");

        View root = inflater.inflate(R.layout.fragment_checkout, container, false);

        //If user already has address, go to payment fragment
        //Else, go to checkout fragment
        //If user already has address, go to payment fragment


        confirm = root.findViewById(R.id.orderBtn);
        address = root.findViewById(R.id.address);
        postcode = root.findViewById(R.id.postcode);
        city = root.findViewById(R.id.city);
        phone = root.findViewById(R.id.phone_number);

        mAuth = FirebaseAuth.getInstance();
        
        //Check if user is logged in
        if (mAuth.getCurrentUser() != null) {
            //User is logged in
            //Get user details and fill in the form
            //Add it to the database
            if (mDatabase.child("address").equals(null)) {
                //Go to login page
                NavController navController = Navigation.findNavController(getActivity(),
                        R.id.nav_host_fragment_content_main_navigation);
                navController.navigate(R.id.nav_payment);
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            } else {
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String addressText = address.getText().toString();
                        String postcodeText = postcode.getText().toString();
                        String cityText = city.getText().toString();
                        String phoneText = phone.getText().toString();
                        String userId = mAuth.getCurrentUser().getUid();
                        mDatabase.child(userId).child("address").setValue(addressText);
                        mDatabase.child(userId).child("postcode").setValue(postcodeText);
                        mDatabase.child(userId).child("city").setValue(cityText);
                        mDatabase.child(userId).child("phone").setValue(phoneText);
                        NavController navController = Navigation.findNavController(getActivity(),
                                R.id.nav_host_fragment_content_main_navigation);
                        navController.navigate(R.id.nav_payment);
                    }
                });
            }
        } else {
            //Go to login page
            NavController navController = Navigation.findNavController(getActivity(),
                    R.id.nav_host_fragment_content_main_navigation);
            navController.navigate(R.id.nav_login);
        }
        
        return root;
    }

}

