package com.example.shopproject21514586.Product;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject21514586.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Product> products;

    public ProductsAdapter(List<Product> products) {
        this.products = products;
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);

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

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productDescription;
        TextView productPrice;
        Button addToCartButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.item_image);
            productName = itemView.findViewById(R.id.item_name);
            productDescription = itemView.findViewById(R.id.item_description);
            productPrice = itemView.findViewById(R.id.item_price);
            addToCartButton = itemView.findViewById(R.id.BtnBasket);
        }

        public void bind(Product product) {
            // Bind the data to the views in the ViewHolder
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText("Price: " + product.getPrice());
//            productPrice.setText((int) product.getPrice());
            // Load the product image using Glide or any other image loading library
            Glide.with(itemView.getContext())
                    .load(product.getImageUrl())
                    .into(productImage);
        }
    }

}

