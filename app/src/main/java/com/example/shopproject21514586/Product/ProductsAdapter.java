package com.example.shopproject21514586.Product;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject21514586.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    // Find the RecyclerView in the layout file of the fragment
    private List<Product> products;

    public ProductsAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Product product = this.products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productDescription;
        TextView productPrice;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.item_image);
            productName = itemView.findViewById(R.id.item_name);
            productDescription = itemView.findViewById(R.id.item_description);
            productPrice = itemView.findViewById(R.id.item_price);
        }

        public void bind(Product product) {
            // Bind the data to the views in the ViewHolder
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText("Price: " + product.getPrice());
            Glide.with(itemView.getContext()).load(product.getImageUrl()).into(productImage);
//            productPrice.setText((int) product.getPrice());
            // Load the product image using Glide or any other image loading library
            Glide.with(itemView.getContext())
                    .load(product.getImageUrl())
                    .into(productImage);
        }
    }
}

