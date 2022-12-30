package com.example.shopproject21514586.ui.checkout;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopproject21514586.R;

public class PaymentFragment extends Fragment {

    private EditText cardNumberEditText;
    private EditText expirationDateEditText;
    private EditText cvvEditText;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        cardNumberEditText = view.findViewById(R.id.edit_text_card_number);
        expirationDateEditText = view.findViewById(R.id.edit_text_expiration_date);
        cvvEditText = view.findViewById(R.id.edit_text_cvv);
        submitButton = view.findViewById(R.id.button_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = cardNumberEditText.getText().toString().trim();
                String expirationDate = expirationDateEditText.getText().toString().trim();
                String cvv = cvvEditText.getText().toString().trim();

                if (cardNumber.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a valid card number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (expirationDate.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a valid expiration date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cvv.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a valid CVV", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Go to confirmation page
                NavController navController = Navigation.findNavController(getActivity(),
                        R.id.nav_host_fragment_content_main_navigation);
                navController.navigate(R.id.nav_confirmation);
            }
        });

        return view;
    }

}
