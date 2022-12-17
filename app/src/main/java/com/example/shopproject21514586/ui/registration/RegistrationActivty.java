package com.example.shopproject21514586.ui.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopproject21514586.R;
import com.example.shopproject21514586.UserActivities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class RegistrationActivty extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email_;
    EditText password_;
    EditText confirmPassword_;
    Button RegistrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration);
        RegistrationButton = findViewById(R.id.RegistrationButton);
        email_ = findViewById(R.id.inputUsername);
        password_ = findViewById(R.id.inputPassword);
        confirmPassword_ = findViewById(R.id.inputPassword2);
        mAuth = FirebaseAuth.getInstance();


        register();
    }

    private void register() {
        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_.getText().toString();
                String password = password_.getText().toString();
                String confirmPassword = confirmPassword_.getText().toString();
                if (email.isEmpty()) {
                    email_.setError("Please enter email");
                    email_.requestFocus();
                } else if (password.isEmpty()) {
                    password_.setError("Please enter password");
                    password_.requestFocus();
                } else if (confirmPassword.isEmpty()) {
                    confirmPassword_.setError("Please enter password");
                    confirmPassword_.requestFocus();
                } else if (!(email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty())) {
                    if (password.equals(confirmPassword)) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivty.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivty.this, "Registration failed, please try again", Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegistrationActivty.this, MainActivity.class));
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(RegistrationActivty.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(RegistrationActivty.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.RegistrationButton).setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivty.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistrationActivty.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}