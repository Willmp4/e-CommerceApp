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
    FirebaseAuth mAuth;

    TextView email1;
    TextView verified;
    FirebaseUser user;

    private @NonNull FragmentMyAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        View view =  inflater.inflate(fragment_my_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        email1 = view.findViewById(R.id.UserEmail_);
        verified = view.findViewById(R.id.UserVerified);

        user = mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            email1.setText("Email: " + email);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            if (emailVerified){
                verified.setText("Verified");
            }
            else{
                verified.setText("Not Verified");
            }
        } else {
            // No user is signed in
            Toast.makeText(getActivity(), "No account, need to register", Toast.LENGTH_SHORT).show();
        }
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}