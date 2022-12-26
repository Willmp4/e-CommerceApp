package com.example.shopproject21514586.UserActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject21514586.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.shopproject21514586.databinding.ActivityMainNavigationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class  MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button registerButton;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainNavigationBinding binding;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView show_email;
    TextView userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);



        binding = ActivityMainNavigationBinding.inflate(getLayoutInflater());
        //Link to database
        Paper.init(this);
        setContentView(binding.getRoot());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_account, R.id.nav_login, R.id.nav_logout, R.id.nav_home, R.id.nav_registration, R.id.nav_shopping_basket )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);

        userName = headerView.findViewById(R.id.userName);

        mAuth = FirebaseAuth.getInstance();
        show_email = headerView.findViewById(R.id.show_email);



        }


    //Check if user is logged in
    public void onStart() {
        super.onStart();

        //TODO: Check if user is logged in
        String email = Paper.book().read("email");
        String password = Paper.book().read("password");
        String rememberMe = Paper.book().read("rememberMe");
        if ("true".equals(rememberMe) && email != null && password != null) {
            mAuth = FirebaseAuth.getInstance();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Login successful
                                // Update the Navigation View header with the user's email
                                show_email.setText(mAuth.getCurrentUser().getEmail());
                                //Make log in and register invisible
                                Menu nav_Menu = binding.navView.getMenu();
                                nav_Menu.findItem(R.id.nav_login).setVisible(false);
                                nav_Menu.findItem(R.id.nav_registration).setVisible(false);

                            } else {
                                // Login failed
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        if (!"true".equals(Paper.book().read("rememberMe"))) {
            Paper.book().destroy();
            mAuth.signOut();
            show_email.setText("Not logged in");
        }
        DatabaseReference usersRef = FirebaseDatabase.getInstance("https://shopapp-d8c31-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        if(mAuth.getCurrentUser() != null) {
            usersRef.child(mAuth.getCurrentUser().getUid()).child("firstName").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("TAG", "onDataChange: " + dataSnapshot.getValue());
                    String firstName = dataSnapshot.getValue(String.class);
                    userName.setText(firstName);
                    show_email.setText(mAuth.getCurrentUser().getEmail());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });
        }else {
            userName.setText("Guest");
        }


    }

    //onDestroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}



