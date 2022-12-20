package com.example.shopproject21514586.ui.home.categories.cpus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.shopproject21514586.R;
import com.example.shopproject21514586.databinding.FragmentCpusBinding;

public class CpusFragment extends Fragment {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private AppBarConfiguration mAppBarConfiguration;
    private FragmentCpusBinding binding;

    Button nav_home;

   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_cpus, container, false);

       binding = FragmentCpusBinding.inflate(getLayoutInflater());

//         nav_home = root.findViewById(R.id.nav_home);
//            nav_home.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main_navigation);
//                    navController.navigate(R.id.nav_home);
//                }
//            });
       return root;

   }
}
