package com.example.shopproject21514586.ui.shopping_basket;

import static com.example.shopproject21514586.R.layout.fragment_shopping_basket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject21514586.Product.CardViewAdapter;
import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.Product.ProductsAdapter;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.basket.Basket;

public class ShoppingBasketFragment extends Fragment {
    private Basket basket;
    private RecyclerView basketRecyclerView;
    private ProductsAdapter productAdapter;
    private Button checkoutButton;
    private TextView total;

    private Bundle bundle;

    CardViewAdapter cardViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize the basket
        basket = Basket.getInstance();

        // Inflate the layout for this fragment
        View view = inflater.inflate(fragment_shopping_basket, container, false);

        // Set up the recycler view to display the products in the basket
        total = view.findViewById(R.id.basketTotal);
        basketRecyclerView = view.findViewById(R.id.basket_recylcer);
        basketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter if it is null, otherwise set the adapter to the existing instance
        if (cardViewAdapter == null) {
            cardViewAdapter = new CardViewAdapter(basket.getProducts(), R.layout.checkout_card_view, bundle);

        }

        basketRecyclerView.setAdapter(cardViewAdapter);

        // Set up a button to clear the basket
        Button clearBasketButton = view.findViewById(R.id.clear_basket_button);
        clearBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basket.clear();
                cardViewAdapter.notifyDataSetChanged();
            }
        });

        // Set up a button to checkout
        checkoutButton = view.findViewById(R.id.checkOut);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(),
                        R.id.nav_host_fragment_content_main_navigation);
                navController.navigate(R.id.nav_checkout);
            }
        });

        return view;
    }

    //On start of fragment
    @Override
    public void onStart() {
        super.onStart();
        // Update the adapter with the current products in the basket
        // Display the total price of the basket
        total.setText("Total: " + "£" + basket.getTotal());
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (Product product : basket.getProducts()) {
            outState.putInt(product.getId(), product.getCount());
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Iterate through the products in the basket
            for (Product product : basket.getProducts()) {
                // If the saved instance state contains the count for the product, set the count for the product
                if (savedInstanceState.containsKey(product.getId())) {
                    int count = savedInstanceState.getInt(product.getId());
                    product.setCount(count);
                }
            }
        }
    }



}

