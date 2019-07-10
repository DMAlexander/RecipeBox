package com.example.devin.recipiebox.view.RecipieFolders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.MainMenu;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;

import java.util.ArrayList;

public class RecipieFolder extends AppCompatActivity {

    private static final String TAG = "RecipeFolderActivity";
    RecyclerView recyclerView;
    private EditText editable_recipie_folder_item;
    private DatabaseHelper mDatabaseHelper;
    private RecipieFolderAdapter mAdapter;
    private Button btnRecipieFolderAdd;
    int sizeOfList = 1;
    ImageView mImageBtn;
    MenuItem mCartIconMenuItem;
    Toolbar mMyToolbar;
    TextView mCountTv;
    private ArrayList<RecipieFolderItem> arrayList;
    private RelativeLayout parentRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_folder);
        parentRelativeLayout = (RelativeLayout) findViewById(R.id.parentRelativeLayout);
        btnRecipieFolderAdd = (Button) findViewById(R.id.btnRecipieFolderAdd);
        editable_recipie_folder_item = (EditText) findViewById(R.id.editable_recipie_folder_item);
        mDatabaseHelper = new DatabaseHelper(this);
    //    mMyToolbar = findViewById(R.id.myToolBar);
    //    setSupportActionBar(mMyToolbar);
    //    mMyToolbar.setTitleTextColor(0xFFFFFFFF);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new RecipieFolderAdapter(this, getAllItems());
//        recyclerView.setAdapter(mAdapter);

        setButtons();

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        mAdapter = new RecipieFolderAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        final int childCount = parentRelativeLayout.getChildCount();

    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getRecipieFolderData();
    }

    public void insertItem(String recipieFolderName) {
        String newEntry = editable_recipie_folder_item.getText().toString();
        if(editable_recipie_folder_item != null) {
            boolean insertData = mDatabaseHelper.addRecipieFolderData(newEntry);
            if (insertData) {
                toastMessage("Data successfully inserted!");
                mAdapter.notifyDataSetChanged();
                finish();
                startActivity(getIntent());
            } else {
                toastMessage("Something went wrong!");
            }
        } else {
            toastMessage("Put something in the text field!");
        }
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
                Intent intent = new Intent(RecipieFolder.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });
        int shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf(shoppingCartCount);
        mCountTv.setText(shoppingCartString);

        return super.onCreateOptionsMenu(menu);
    }

    public void setButtons() {
        btnRecipieFolderAdd = findViewById(R.id.btnRecipieFolderAdd);
        btnRecipieFolderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipieFolderName = editable_recipie_folder_item.getText().toString();
                if (editable_recipie_folder_item.length() !=0) {
                    insertItem(recipieFolderName);
                    editable_recipie_folder_item.setText("");
                } else {
                    toastMessage("Please put something in the textbox!");
                }
            }
        });
    }

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_ingredient_layout_field, null);
        //Add the new row before the add field button
        sizeOfList++;
        parentRelativeLayout.addView(rowView, parentRelativeLayout.getChildCount() -1 );
    }
    public void onDelete(View v) {
        parentRelativeLayout.removeView((View) v.getParent());
        sizeOfList--;
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
