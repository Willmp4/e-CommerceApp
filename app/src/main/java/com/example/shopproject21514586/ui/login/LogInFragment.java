package com.example.shopproject21514586.ui.login;

import static com.example.shopproject21514586.R.layout.fragment_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopproject21514586.R;
import com.example.shopproject21514586.UserActivities.MainActivity;

import com.example.shopproject21514586.databinding.FragmentLoginBinding;
import com.example.shopproject21514586.ui.registration.RegistrationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;


public class LogInFragment extends Fragment{
    EditText email_;
    EditText password_;
    Button logInButton;
    Button signUpButton;
    FirebaseAuth mAuth;
    Button adminButton;
    private CheckBox rememberMe;
    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(fragment_login, container, false);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        email_ = view.findViewById(R.id.email_input);
        password_ = view.findViewById(R.id.inputPassword);
        rememberMe = view.findViewById(R.id.checkbox);
        Paper.init(getContext());
        mAuth = FirebaseAuth.getInstance();

        logInButton = view.findViewById(R.id.logInButton);
        //On click listener for the sign in button
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //On click listener for the Admin button
        adminButton = view.findViewById(R.id.BtnAdmin);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch to the admin fragment
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main_navigation);
                navController.navigate(R.id.nav_admin);
            }
        });

        //On click listener for the sign up button
        signUpButton = view.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegistrationFragment registrationFragment = new RegistrationFragment();
                fragmentTransaction.replace(getId(), registrationFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //Go to home fragment
            NavController navController = Navigation.findNavController(getActivity(),
                    R.id.nav_host_fragment_content_main_navigation);
            navController.navigate(R.id.nav_home);
        }
    }

    //remember me check box
    private void rememberMe(String email, String password) {
        //remember me check box
        if (rememberMe.isChecked()) {
            //save username and password
           Paper.book().write("email", email);
            Paper.book().write("password", password);
            Paper.book().write("rememberMe", "true");
        }
    }

    private void signIn() {
        String email = email_.getText().toString();
        String password = password_.getText().toString();

        //check if username and are empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter email and password", Toast.LENGTH_SHORT).show();
        } else {
            //Sign in with email and password firebase method
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                rememberMe(email, password);

                                Toast.makeText(getActivity(), "Authentication Success!", Toast.LENGTH_SHORT).show();
                                //Intent
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getActivity(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

//    private void reload() {
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            // User is signed in
//            Log.d("TAG", "reload:signed_in");
//        } else {
//            // User is signed out
//            Log.d("TAG", "reload:signed_out");
//        }
//    }

//    public void onClick(View v) {
//        if (v.getId() != view.findViewById(logInButton)) {
//            return;
//        }
//        signIn();
//
//
//    }
}
