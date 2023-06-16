package com.example.finalproject.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class ProductNestedAdapter extends RecyclerView.Adapter<ProductNestedAdapter.ProductViewHolder> {

    private final ArrayList<Product> mList;
    private final Context context;

    public ProductNestedAdapter(ArrayList<Product> products, Context context) {
        mList = MockupData.getProductsList();
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mList.get(position);

        holder.textView_title.setText(product.getTitle());
        holder.textView_description.setText(product.getDescription());

        String productPrice = product.getPrice() + "$";
        holder.textView_price.setText(productPrice);

        holder.imageView_image.setImageResource(R.drawable.card_image_placeholder);

        holder.button_add.setOnClickListener(v -> {
            Toast.makeText(context, "ADDED ITEM", Toast.LENGTH_SHORT).show();

            //ADD ITEM TO CUSTOMER CART
        });

        holder.button_view.setOnClickListener(v -> {
            Toast.makeText(context, "VIEWING ITEM", Toast.LENGTH_SHORT).show();

            //OPEN DETAILED VIEW OF ITEM
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_title;
        private final TextView textView_description;
        private final TextView textView_price;
        private final ShapeableImageView imageView_image;
        private final AppCompatButton button_view;
        private final AppCompatButton button_add;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_title = itemView.findViewById(R.id.card_item_title);
            textView_description = itemView.findViewById(R.id.card_item_description);
            textView_price = itemView.findViewById(R.id.card_item_price);
            imageView_image = itemView.findViewById(R.id.card_item_image);

            button_add = itemView.findViewById(R.id.card_item_btn_add);
            button_view = itemView.findViewById(R.id.card_item_btn_view);

        }
    }
}
