package com.example.shopproject21514586.ui.login;

import static android.content.ContentValues.TAG;

import static com.example.shopproject21514586.R.layout.fragment_login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopproject21514586.R;
import com.example.shopproject21514586.UserActivities.MainActivity;

import com.example.shopproject21514586.databinding.FragmentLoginBinding;
import com.example.shopproject21514586.ui.logout.LogOutFragment;
import com.example.shopproject21514586.ui.registration.RegistrationActivty;
import com.example.shopproject21514586.ui.registration.RegistrationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LogInFragment extends Fragment{
    EditText email_;
    EditText password_;
    Button logInButton;
    Button signUpButton;
    FirebaseAuth mAuth;
    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view =  inflater.inflate(fragment_login, container, false);
        email_ = view.findViewById(R.id.inputUsername);
        password_ = view.findViewById(R.id.inputPassword);

        mAuth = FirebaseAuth.getInstance();

        logInButton = view.findViewById(R.id.logInButton);

        //On click listener for the sign in button

        logInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        signUpButton = view.findViewById(R.id.signUpButton);

        //On click listener for the sign in button

        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //go to registration fragment
                Intent intent = new Intent(getActivity(), RegistrationActivty.class);
                startActivity(intent);

            }
        });
//

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
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

//    //remember me check box
//    private void rememberMe(){
//        //remember me check box
//        CheckBox rememberMe = findViewById(R.id.checkbox);
//        if(rememberMe.isChecked()){
//            //save username and password
//
//
//        }

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
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getActivity(), "Authentication Success!", Toast.LENGTH_SHORT).show();
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
