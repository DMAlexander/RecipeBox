package com.example.devin.recipiebox.view.ShoppingCart;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.MainMenu;
import com.example.devin.recipiebox.view.Recipie.MainActivity;
import com.example.devin.recipiebox.view.RecipieFolders.RecipieFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShoppingCartList extends AppCompatActivity {

    private static final String TAG = "ShoppingCartList";

    DatabaseHelper mDatabaseHelper;
  //  private ListView mListView;
    private Button btnNavigate;
    private String selectedName;
    private int selectedID;
    String selectedRecipieName;
    private ShoppingCartAdapter mAdapter;
    private ShoppingCartDialogAdapter mAdapter2;
    ImageButton mImageBtn;
    Toolbar mMyToolbar;
    TextView mCountTv, tv;
    MenuItem mCartIconMenuItem;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_shopping_cart_list );
        mDatabaseHelper = new DatabaseHelper(this );
        btnNavigate = (Button) findViewById( R.id.btnNavigate );
        mDatabaseHelper = new DatabaseHelper(this );
        RecyclerView recyclerView = findViewById( R.id.recyclerView );
        TextView tv = findViewById( R.id.ingredientName );
        recyclerView.setLayoutManager( new LinearLayoutManager(this ) );
        Intent recievedIntent = getIntent();
        selectedRecipieName = recievedIntent.getStringExtra("RecipieName" );

        mAdapter = new ShoppingCartAdapter(this, getAllItems() );
        recyclerView.setAdapter( mAdapter );

        mAdapter.setOnItemClickListener(new ShoppingCartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position ) {

            }

            @Override
            public void onQuantityClick( int position, String ingredientName ) {
                makeQuantityDialog( position, ingredientName );
            }

            @Override
            public void onDeleteClick( int position, String ingredientName, String recipeName ) {
                makeDeleteDialog( position, ingredientName, recipeName );
            }
        });

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartList.this, MainMenu.class);
                startActivity( intent );
            }
        });
    }

    public void removeItem(int position, String ingredientName, String recipeName) {

        Cursor data = mDatabaseHelper.getShoppingCartItemID(ingredientName);
        int itemID = -1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        Log.d(TAG, "ingredientId: " + itemID + " and ingredient name is: " + ingredientName);

//        mDatabaseHelper.deleteShoppingCartIngredient(itemID, ingredientName);
        int id = position+1;
        mDatabaseHelper.deleteShoppingCartRow(id);

        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyDataSetChanged();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        toastMessage("Removed from database");

    }

    public void makeDeleteDialog(final int position, final String ingredientName, final String recipeName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartList.this);
        builder.setTitle("Delete Ingredient");
        builder.setMessage("Are you sure you want to delete the ingredient?");
    //    builder.setView(R.layout.activity_folder_layout_screen);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position, ingredientName, recipeName);
                Toast.makeText(ShoppingCartList.this, "Thanks!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ShoppingCartList.this, "Sorry.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    public void makeQuantityDialog(final int position, String ingredientName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartList.this);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fraglayout);
        mDatabaseHelper = new DatabaseHelper(this);

        dialog.setTitle("Your Recipies: ");

        RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.rv);
        TextView tv = (TextView) dialog.findViewById(R.id.ingredientName);
        tv.setText(ingredientName);

        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter2 = new ShoppingCartDialogAdapter(this, getDialogItems(ingredientName));
        rv.setAdapter(mAdapter2);

        dialog.show();

    }

    //Need this method for shopping cart icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mCartIconMenuItem = menu.findItem(R.id.cart_count_menu_item);
        View actionView = mCartIconMenuItem.getActionView();

        if(actionView != null) {
            mCountTv = actionView.findViewById(R.id.count_tv_layout);
            mImageBtn = actionView.findViewById(R.id.image_btn_layout);
        }
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartList.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });
        int shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf(shoppingCartCount);
        mCountTv.setText(shoppingCartString);

        return super.onCreateOptionsMenu(menu);
    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getShoppingCartData();

    }

    private Cursor getDialogItems(String ingredientName) {

        return mDatabaseHelper.getExportedShoppingCartData(ingredientName);

    }

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
