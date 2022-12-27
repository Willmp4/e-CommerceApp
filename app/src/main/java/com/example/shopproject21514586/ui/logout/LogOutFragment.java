package com.example.shopproject21514586.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopproject21514586.R;
import com.example.shopproject21514586.UserActivities.MainActivity;
import com.example.shopproject21514586.databinding.FragmentLogOutBinding;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;

import java.util.zip.Inflater;

import io.paperdb.Paper;

public class LogOutFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentLogOutBinding binding;
    private TextView show_email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.nav_header_main_navigation, container, false);
        show_email = view.findViewById(R.id.show_email);

        Inflater FragmentSlideshowBinding;
        binding = FragmentLogOutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Paper.init(getContext());
        Paper.book().destroy();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            mAuth.signOut();
            //intent to main activity
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }

        return root;
    }

}