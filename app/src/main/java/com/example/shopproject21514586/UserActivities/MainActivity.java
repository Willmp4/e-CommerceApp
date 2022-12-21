package com.example.shopproject21514586.UserActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.ui.home.HomeFragment;
import com.example.shopproject21514586.ui.home.categories.cpus.CpusFragment;
import com.example.shopproject21514586.ui.registration.RegistrationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.api.Http;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.shopproject21514586.databinding.ActivityMainNavigationBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class  MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button registerButton;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainNavigationBinding binding;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView show_email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

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
        //Set email to navigation header
        show_email = headerView.findViewById(R.id.show_email);
        if(mAuth.getCurrentUser() !=null){
            show_email.setText(mAuth.getCurrentUser().getEmail());
        }


//        TextView show_email = binding.navView.findViewById(R.id.nav_header_main_navigation).findViewById(R.id.show_email);
//        show_email.setText(mAuth.getCurrentUser().getEmail());



    }

    //Check if user is logged in
    public void onStart() {
        super.onStart();
        //variables for reading the data from the Paper
        String email = Paper.book().read("email");
        String password = Paper.book().read("password");
        String rememberMe = Paper.book().read("rememberMe");

        if("true".equals(rememberMe) && email != null && password != null){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Make log in button invisible
                        binding.navView.getMenu().findItem(R.id.nav_login).setVisible(false);
                        //Make register button visible
                        binding.navView.getMenu().findItem(R.id.nav_registration).setVisible(false);
                        show_email.setText(mAuth.getCurrentUser().getEmail());
                        Toast.makeText(MainActivity.this, "Already Logged in", Toast.LENGTH_SHORT).show();
                        }
                    else{
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if("false".equals(rememberMe)){
            show_email.setText("Not logged in");
            mAuth.signOut();
        }

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


