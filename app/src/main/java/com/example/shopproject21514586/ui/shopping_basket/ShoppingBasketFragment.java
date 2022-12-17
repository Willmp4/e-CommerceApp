package com.example.shopproject21514586.ui.shopping_basket;

import static com.example.shopproject21514586.R.layout.fragment_my_account;
import static com.example.shopproject21514586.R.layout.fragment_shopping_basket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopproject21514586.databinding.FragmentShoppingBasketBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ShoppingBasketFragment extends Fragment {
    FirebaseAuth mAuth;
    private @NonNull FragmentShoppingBasketBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShoppingBasketBinding.inflate(inflater, container, false);
        View view =  inflater.inflate(fragment_shopping_basket, container, false);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
