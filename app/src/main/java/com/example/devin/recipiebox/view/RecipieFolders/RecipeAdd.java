package com.example.devin.recipiebox.view.RecipieFolders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

public class RecipeAdd extends AppCompatActivity {

    private Button btnRecipieFolderAdd;
    private EditText editable_recipie_folder_item;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);
        btnRecipieFolderAdd = (Button) findViewById(R.id.btnRecipieFolderAdd);
        editable_recipie_folder_item = (EditText) findViewById(R.id.editable_recipie_folder_item);
        mDatabaseHelper = new DatabaseHelper(this);

        setButtons();
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

    public void insertItem(String recipieFolderName) {
        String newEntry = editable_recipie_folder_item.getText().toString();
        if(editable_recipie_folder_item != null) {
            boolean insertData = mDatabaseHelper.addRecipieFolderData(newEntry);
            if (insertData) {
                toastMessage("Data successfully inserted!");
        //        mAdapter.notifyDataSetChanged();
                btnRecipieFolderAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RecipeAdd.this, RecipieFolder.class);
                        startActivity(intent);
                    }
                });
                finish();
                startActivity(getIntent());
            } else {
                toastMessage("Something went wrong!");
            }
        } else {
            toastMessage("Put something in the text field!");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
