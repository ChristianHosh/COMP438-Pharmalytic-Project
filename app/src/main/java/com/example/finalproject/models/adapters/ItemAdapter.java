package com.example.finalproject.models.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.DetailedActivity;
import com.example.finalproject.R;
import com.example.finalproject.controllers.CartController;
import com.example.finalproject.controllers.SessionController;
import com.example.finalproject.models.Item;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ProductViewHolder> {

    public static final String ITEM_KEY = "ITEM_JSON";
    private final ArrayList<Item> mList;

    private final Context context;

    public ItemAdapter(ArrayList<Item> items, Context context) {
        mList = items;
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
        Item item = mList.get(position);

        holder.textView_title.setText(item.getName());
        holder.textView_description.setText(item.getDescription());

        String productPrice = item.getPrice() + "$";
        holder.textView_price.setText(productPrice);

        holder.imageView_image.setImageResource(R.drawable.unavailable_placeholder_image);

        String url = item.getImage_path().toString();

        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading_spinner)
                .error(R.drawable.unavailable_placeholder_image)
                .into(holder.imageView_image);

        //  ADD ITEM TO CUSTOMER CART
        holder.button_add.setOnClickListener(v -> CartController
                .addItemToCart(SessionController.getInstance(), item, context));

        //  VIEW DETAILED ITEM VIEW
        holder.button_view.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra(ITEM_KEY, new Gson().toJson(item));
            context.startActivity(intent);
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
