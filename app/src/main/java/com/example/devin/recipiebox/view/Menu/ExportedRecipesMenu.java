package com.example.devin.recipiebox.view.Menu;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

public class ExportedRecipesMenu extends AppCompatActivity {

    private static final String TAG = "ExportedRecipesMenu";

    private Button btnRecipes;
    DatabaseHelper mDatabaseHelper;
    private ExportedRecipesAdapter mAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exported_recipes_menu);

        mDatabaseHelper = new DatabaseHelper(this);

        //Recycler View Declaration
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExportedRecipesAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getExportedShoppingCartRecipes();
    }
}
