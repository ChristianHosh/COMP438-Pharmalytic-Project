package com.example.finalproject.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.CommonGlobal;
import com.example.finalproject.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final ArrayList<Category> mList;
    private final Context context;
    private ArrayList<Product> productList = MockupData.getProductsList();


    public CategoryAdapter(ArrayList<Category> mList, Context context) {
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
        Category category = mList.get(position);

        holder.mTextView.setText(category.getName());

        boolean isExpanded = category.isExpanded;
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        if (isExpanded){
            holder.mTextView.setCompoundDrawablesWithIntrinsicBounds(0,0, CommonGlobal.UI.DROP_UP_ARROW,0);
        }else{
            holder.mTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,CommonGlobal.UI.DROP_DOWN_ARROW,0);
        }

        ProductNestedAdapter adapter = new ProductNestedAdapter(productList, context);
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(adapter);
        holder.linearLayout.setOnClickListener(v -> {

            category.isExpanded = !category.isExpanded;
            productList = category.getProducts();
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
