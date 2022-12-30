package com.example.shopproject21514586.ui.myaccount;

import static com.example.shopproject21514586.R.layout.fragment_my_account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopproject21514586.R;
import com.example.shopproject21514586.databinding.FragmentMyAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyAccountFragment extends Fragment {
    // Declare a FirebaseAuth instance
    FirebaseAuth mAuth;

    // Declare two TextViews to display the user's email and verification status
    TextView email1;
    TextView verified;

    // Declare a FirebaseUser instance to represent the currently signed-in user
    FirebaseUser user;

    // Declare a FragmentMyAccountBinding instance for data binding
    private @NonNull FragmentMyAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout and bind it to the binding object
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        View view =  inflater.inflate(fragment_my_account, container, false);

        // Initialize the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Get references to the email and verified TextViews
        email1 = view.findViewById(R.id.UserEmail_);
        verified = view.findViewById(R.id.UserVerified);

        // Get the currently signed-in user
        user = mAuth.getCurrentUser();

        // If there is a signed-in user, display their email and verification status
        if (user != null) {
            // Get the user's email
            String email = user.getEmail();
            // Set the email TextView to display the user's email
            email1.setText("Email: " + email);

            // Check if the user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // Set the verified TextView to display whether the user's email is verified
            if (emailVerified){
                verified.setText("Verified");
            }
            else{
                verified.setText("Not Verified");
            }
        } else {
            // If there is no signed-in user, show a Toast message
            Toast.makeText(getActivity(), "No account, need to register", Toast.LENGTH_SHORT).show();
        }
        return view;

    }

    // Override the onDestroyView method to null out the binding object
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}