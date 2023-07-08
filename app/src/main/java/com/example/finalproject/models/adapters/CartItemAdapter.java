package com.example.finalproject.models.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.controllers.CartController;
import com.example.finalproject.controllers.ProductController;
import com.example.finalproject.controllers.SessionController;
import com.example.finalproject.models.CartItem;
import com.example.finalproject.models.Item;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private static final String TAG = "CartItemAdapter";
    private final ArrayList<CartItem> itemArrayList;
    private final TextView textView_price;
    private final Context context;

    public CartItemAdapter(ArrayList<CartItem> items, Context context, TextView textView) {
        this.itemArrayList = items;
        this.context = context;
        this.textView_price = textView;
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
            // ADD 1 TO QUANTITY
            cartItem.addQuantity();
            Log.w(TAG, "ADDING TO ITEM");

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            firestore
                    .collection(ProductController.USER_COLLECTION)
                    .document(SessionController.getInstance().getId())
                    .collection(CartController.USER_COLLECTION_CART)
                    .document(cartItem.getId())
                    .update(CartController.USER_COLLECTION_CART_FIELD_QUANTITY, cartItem.getQuantity(),
                            CartController.USER_COLLECTION_CART_FIELD_TOTAL_PRICE, cartItem.getPrice())

                    .addOnSuccessListener(result -> {
                        Toast.makeText(context, "Added one " + item.getName(), Toast.LENGTH_SHORT).show();
                        notifyItemChanged(position);

                    })
                    .addOnFailureListener(exception -> {
                        Toast.makeText(context, "Couldn't Add " + item.getName(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "ERROR UPDATING QUANTITY => ", exception);
                        cartItem.removeQuantity();
                        notifyItemChanged(position);
                    });

            CartController.updateTotal(this.itemArrayList, textView_price);

        });

        //  REMOVE ITEM QUANTITY TO CART ITEM
        holder.button_remove.setOnClickListener(v ->

        {
            // REMOVE 1 TO QUANTITY
            // IF QUANTITY BECOMES 0 REMOVE FROM CART AND FROM RECYCLER
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            cartItem.removeQuantity();

            Log.w(TAG, "REMOVING ITEM");
            if (cartItem.getQuantity() <= 0) {
                // REMOVE ITEM FROM CART
                firestore
                        .collection(ProductController.USER_COLLECTION)
                        .document(SessionController.getInstance().getId())
                        .collection(CartController.USER_COLLECTION_CART)
                        .document(cartItem.getId())
                        .delete()

                        .addOnSuccessListener(result -> {
                            Toast.makeText(context, "Removed " + item.getName() + " From Cart", Toast.LENGTH_SHORT).show();
                            this.itemArrayList.remove(position);
                            notifyItemRemoved(position);

                        })
                        .addOnFailureListener(exception -> {
                            Toast.makeText(context, "Couldn't Remove " + item.getName(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "ERROR DELETING DOCUMENT => ", exception);
                            cartItem.addQuantity();
                            notifyItemChanged(position);
                        });


            } else {
                // UPDATE ITEM QUANTITY
                firestore
                        .collection(ProductController.USER_COLLECTION)
                        .document(SessionController.getInstance().getId())
                        .collection(CartController.USER_COLLECTION_CART)
                        .document(cartItem.getId())
                        .update(CartController.USER_COLLECTION_CART_FIELD_QUANTITY, cartItem.getQuantity(),
                                CartController.USER_COLLECTION_CART_FIELD_TOTAL_PRICE, cartItem.getPrice())

                        .addOnSuccessListener(result -> {
                            Toast.makeText(context, "Removed one " + item.getName(), Toast.LENGTH_SHORT).show();
                            notifyItemChanged(position);

                        })
                        .addOnFailureListener(exception -> {
                            Toast.makeText(context, "Couldn't Remove " + item.getName(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "ERROR UPDATING QUANTITY => ", exception);
                            cartItem.addQuantity();
                            notifyItemChanged(position);
                        });
            }
            CartController.updateTotal(this.itemArrayList, textView_price);
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
