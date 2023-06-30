package com.example.finalproject.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.globals.CommonGlobal;
import com.example.finalproject.globals.DatabaseController;
import com.example.finalproject.models.Product;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final ArrayList<Product> mList;
    private final Context context;

    public CategoryAdapter(ArrayList<Product> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_category , parent , false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Product category = mList.get(position);

        holder.mTextView.setText(category.getName());

        boolean isExpanded = category.isExpanded;
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        if (isExpanded){
            holder.mTextView.setCompoundDrawablesWithIntrinsicBounds(0,0, CommonGlobal.UI.DROP_UP_ARROW,0);
            DatabaseController.initItemsFromProduct(category, holder.nestedRecyclerView, context, holder.itemView.getContext());

        }else{
            holder.mTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,CommonGlobal.UI.DROP_DOWN_ARROW,0);
        }

        holder.linearLayout.setOnClickListener(v -> {
            category.isExpanded = !category.isExpanded;
            notifyItemChanged(holder.getAdapterPosition());
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayoutCompat linearLayout;
        private final RelativeLayout expandableLayout;
        private final TextView mTextView;
        private final RecyclerView nestedRecyclerView;
        public CategoryViewHolder(View cardView){
            super(cardView);

            linearLayout = itemView.findViewById(R.id.card_category_linear);
            expandableLayout = itemView.findViewById(R.id.card_category_expandable_layout);
            mTextView = itemView.findViewById(R.id.card_category_label);
            nestedRecyclerView = itemView.findViewById(R.id.card_category_child_recycler);
        }

    }
}
