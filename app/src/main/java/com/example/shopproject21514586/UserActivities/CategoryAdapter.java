package com.example.shopproject21514586.UserActivities;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject21514586.Product.Product;
import com.example.shopproject21514586.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categories;


    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the name and image for the category
        Category category = categories.get(position);
        //To de bug
        Log.d("CategoryAdapter", "onBindViewHolder: " + category.getName());

        holder.nameTextView.setText(category.getName());

        // Set the click listener for the category
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.categoryView);
            // Set an onClickListener on the root view of the ViewHolder

        }
        public void bind(Category category) {
            // Bind the data to the views in the ViewHolder
            nameTextView.setText(category.getName());
        }

    }
}

