package com.example.devin.recipiebox.view.ShoppingCart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

public class EditShoppingCart extends AppCompatActivity {

    private static final String TAG = "EditShoppingCartAct";

    private Button btnSave, btnDelete;
    private EditText editable_item;
    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopping_cart);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");

        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if (!item.equals("")) {
                    mDatabaseHelper.updateShoppingCartName(item, selectedID, selectedName);
                    Log.d(TAG, "Shopping cart name has updated");
                    Intent intent = new Intent(EditShoppingCart.this, ShoppingCartList.class);
                    startActivity(intent);
                } else {
                    toastMessage("Enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteShoppingCartRecipie(selectedID, selectedName);
                editable_item.setText("");
                toastMessage("removed from database");
                Intent intent = new Intent(EditShoppingCart.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
