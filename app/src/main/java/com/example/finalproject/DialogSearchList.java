package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.Item;
import com.example.finalproject.models.adapters.ItemAdapter;

import java.util.ArrayList;

public class DialogSearchList extends Dialog {
    private final ArrayList<Item> list;

    public DialogSearchList(Context context, ArrayList<Item> list)
    {
        super(context);
        this.list = list;
    }

    // This method is called when the Dialog is created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());

        // Use the LayoutInflater to inflate the
        // dialog_list layout file into a View object
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_search, null);

        setContentView(view);
        getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);

        setCanceledOnTouchOutside(true);

        setCancelable(true);

        setUpRecyclerView(view);
    }

    // This method sets up the RecyclerView in the dialog
    private void setUpRecyclerView(View view)
    {
        // Find the RecyclerView in the layout file and set
        // its layout manager to a LinearLayoutManager
        RecyclerView recyclerView = view.findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create a new instance of the EmployeeAdapter
        // and set it as the RecyclerView's adapter
        ItemAdapter adapter = new ItemAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
    }
}
