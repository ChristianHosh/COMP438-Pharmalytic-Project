package com.example.finalproject.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.CartItem;
import com.example.finalproject.models.Item;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private final ArrayList<CartItem> itemArrayList;
    private final Context context;

    public CartItemAdapter(ArrayList<CartItem> items, Context context) {
        this.itemArrayList = items;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemAdapter.CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cart_item, parent, false);
        return new CartItemAdapter.CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.CartItemViewHolder holder, int position) {
        CartItem cartItem = itemArrayList.get(position);
        Item item = cartItem.getItem();

        holder.textView_title.setText(item.getName());

        String productPrice = item.getPrice() + "$";
        holder.textView_price.setText(productPrice);

        holder.textView_quantity.setText(String.valueOf(cartItem.getQuantity()));

        holder.imageView_image.setImageResource(R.drawable.unavailable_placeholder_image);

        String url = item.getImage_path().toString();

        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading_spinner)
                .error(R.drawable.unavailable_placeholder_image)
                .into(holder.imageView_image);

        //  ADD ITEM QUANTITY TO CART ITEM
        holder.button_add.setOnClickListener(v -> {

        });

        //  REMOVE ITEM QUANTITY TO CART ITEM
        holder.button_remove.setOnClickListener(v -> {

        });


    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_title;
        private final TextView textView_price;
        private final TextView textView_quantity;
        private final ShapeableImageView imageView_image;
        private final AppCompatButton button_add;
        private final AppCompatButton button_remove;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_title = itemView.findViewById(R.id.card_cart_item_title);
            textView_quantity = itemView.findViewById(R.id.card_cart_text_quantity);
            textView_price = itemView.findViewById(R.id.card_cart_item_price);
            imageView_image = itemView.findViewById(R.id.card_cart_item_image);

            button_add = itemView.findViewById(R.id.card_cart_btn_addQuantity);
            button_remove = itemView.findViewById(R.id.card_cart_btn_removeQuantity);
        }
    }
}
