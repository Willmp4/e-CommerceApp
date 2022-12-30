package com.example.shopproject21514586.Product;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject21514586.R;
import com.example.shopproject21514586.basket.Basket;
import com.example.shopproject21514586.ui.shopping_basket.ShoppingBasketFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<Product> products;
    private int layoutResourceId;
    Basket basket = Basket.getInstance();



    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    //get product count

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    private View.OnClickListener onButtonClickListener;

    public void setOnButtonClickListener(View.OnClickListener listener) {
        this.onButtonClickListener = listener;
    }



    private Bundle bundle;

    public CardViewAdapter(List<Product> products, int layoutResourceId, Bundle bundle) {
        this.products = products;
        this.layoutResourceId = layoutResourceId;
        this.bundle = bundle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the name and image for the product
        final Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("£" + String.valueOf(product.getPrice()));
        Glide.with(holder.productImage.getContext()).load(product.getImageUrl()).into(holder.productImage);

        // Check if the layout contains the add and subtract buttons
        if (holder.addButton != null && holder.subtractButton != null && holder.countTextView != null) {
            // Check if the bundle contains a count for this product, and if so set the product count to the value in the bundle
            if (bundle != null && bundle.containsKey(product.getId())) {
                int count = bundle.getInt(product.getId());
                product.setCount(count);
                holder.productPrice.setText("£" + String.valueOf(product.getPrice() * product.getCount()));

            }
            holder.countTextView.setText(String.valueOf(product.getCount()));
            holder.addButton.setOnClickListener(v -> {
                product.setCount(product.getCount() + 1);
                holder.countTextView.setText(String.valueOf(product.getCount()));
                holder.productPrice.setText("£" + String.valueOf(product.getPrice() * product.getCount()));
                TextView total = v.getRootView().findViewById(R.id.basketTotal);
                total.setText("Total: " + "£" + String.valueOf(basket.getTotal()));
                Log.d("total", total.getText().toString());


                // Store the count in the Bundle when the fragment is paused or stopped
                if(bundle != null) {
                    bundle.putInt(product.getId(), product.getCount());
                }
            });

            holder.subtractButton.setOnClickListener(v -> {
                if (product.getCount() > 0) {
                    product.setCount(product.getCount() - 1);
                    holder.countTextView.setText(String.valueOf(product.getCount()));
                    holder.productPrice.setText("£" + String.valueOf(product.getPrice() * product.getCount()));
                    TextView total = v.getRootView().findViewById(R.id.basketTotal);
                    total.setText("Total: " + "£"+ basket.getTotal() );

                    // Store the count in the Bundle when the fragment is paused or stopped
                    if(bundle != null) {
                        bundle.putInt(product.getId(), product.getCount());
                    }
                    // If the count is 0, remove the product from the basket
                    if (product.getCount() == 0) {
                        basket.removeProduct(product);
                    }
                }else {
                    // If the count is 0, remove the product from the basket
                    basket.removeProduct(product);
                    products.remove(product);
                    notifyDataSetChanged();
                }
            });
        }

        // Set the click listener for the category
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(product);
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        ImageView productImage;
        FloatingActionButton addButton;
        FloatingActionButton subtractButton;
        TextView countTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.item_name);
            productPrice = itemView.findViewById(R.id.item_price);
            productImage = itemView.findViewById(R.id.item_image);
            addButton = itemView.findViewById(R.id.plusButton);
            subtractButton = itemView.findViewById(R.id.minusButton);
            countTextView = itemView.findViewById(R.id.item_quantity);

            // Set an onClickListener on the root view of the ViewHolder
        }

        public void bind(Product product) {
            // Bind the data to the views in the ViewHolder

            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
            Glide.with(productImage.getContext()).load(product.getImageUrl()).into(productImage);
        }

    }
}

