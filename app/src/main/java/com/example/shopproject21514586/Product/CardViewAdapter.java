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
import com.example.shopproject21514586.ui.category.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<Product> products;


    public CardViewAdapter(List<Product> products) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the name and image for the category
        Product product = products.get(position);
        //To de bug
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productDescription.setText(product.getDescription());
        Glide.with(holder.productImage.getContext()).load(product.getImageUrl()).into(holder.productImage);


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
        TextView productDescription;
        ImageView productImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.item_name);
            productPrice = itemView.findViewById(R.id.item_price);
            productDescription = itemView.findViewById(R.id.item_description);
            productImage = itemView.findViewById(R.id.item_image);

            // Set an onClickListener on the root view of the ViewHolder
        }

        public void bind(Product product) {
            // Bind the data to the views in the ViewHolder

            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
            productDescription.setText(product.getDescription());
            Glide.with(productImage.getContext()).load(product.getImageUrl()).into(productImage);
        }
    }
}

