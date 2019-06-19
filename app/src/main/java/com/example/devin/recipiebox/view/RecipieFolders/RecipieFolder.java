package com.example.devin.recipiebox.view.RecipieFolders;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

import java.util.ArrayList;

public class RecipieFolder extends AppCompatActivity {

    private static final String TAG = "RecipeFolderActivity";
    RecyclerView recyclerView;

    private DatabaseHelper mDatabaseHelper;
  //  private RecipieFolderAdapter mAdapter;

    private ArrayList<RecipieFolderItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_folder);

        mDatabaseHelper = new DatabaseHelper(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new RecipieFolderAdapter(this, getAllItems());
//        recyclerView.setAdapter(mAdapter);



        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        RecipieFolderAdapter mAdapter = new RecipieFolderAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getRecipieData();
    }
}
