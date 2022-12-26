package com.example.shopproject21514586.ui.registration;

import static com.example.shopproject21514586.R.layout.fragment_registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.UserActivities.MainActivity;
import com.example.shopproject21514586.databinding.FragmentLoginBinding;

import com.example.shopproject21514586.ui.login.LogInFragment;
import com.example.shopproject21514586.users.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationFragment extends Fragment {
    EditText email_;
    EditText password_;
    Button RegistrationButton;
    EditText confirmPassword_;
    FirebaseAuth mAuth;
    EditText FirstName;
    EditText LastName;
    DatabaseReference ref;
    FirebaseUser user;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(fragment_registration, container, false);
        email_ = view.findViewById(R.id.email_input);
        password_ = view.findViewById(R.id.password);
        FirstName = view.findViewById(R.id.firstName);
        LastName = view.findViewById(R.id.lastName);
        confirmPassword_ = view.findViewById(R.id.confirm_password);
        mAuth = FirebaseAuth.getInstance();
        RegistrationButton = view.findViewById(R.id.RegistrationButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/");
         ref = database.getReference().child("Users");

        register(view);
        return view;
    }

    private void register(View v) {
        RegistrationButton = v.findViewById(R.id.RegistrationButton);
        //On click listener for the sign in button
        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
                String email = email_.getText().toString();
                String password = password_.getText().toString();
                String confirmPassword = confirmPassword_.getText().toString();
                String firstName = FirstName.getText().toString();
                String lastName = LastName.getText().toString();

                //check if username and are empty
                if (email.isEmpty() || (password.isEmpty() && confirmPassword.isEmpty())
                        || FirstName.getText().toString().isEmpty() || LastName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    //create user
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        sendEmailVerification(v);
                                        ref.child(user.getUid()).setValue(new Users(mAuth.getCurrentUser().getUid(), firstName, lastName, email, password, ""));



                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getActivity(), "Registration failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }

        });
    }
        private void sendEmailVerification (View view){
            final FirebaseUser user = mAuth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            // Re-enable button
                            view.findViewById(R.id.RegistrationButton).setEnabled(true);
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),
                                        "Verification email sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(),
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
