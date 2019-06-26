package com.example.devin.recipiebox.view.Recipie;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Ingredient.IngredientScreen;
import com.example.devin.recipiebox.view.NewIngredient.IngredientLayoutScreen;

public class RecipieInsert extends AppCompatActivity {

    private static final String TAG = "RecipieInsert";

    DatabaseHelper mDatabaseHelper;
    private Button btnRecipeAdd;
    private EditText editRecipieText;
    private int selectedRecipieFolderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_insert);
        btnRecipeAdd = (Button) findViewById(R.id.btnRecipieAdd);
        editRecipieText = (EditText) findViewById(R.id.editRecipieText);
        mDatabaseHelper = new DatabaseHelper(this);
        getSupportActionBar().setTitle("Recipe Insert Screen");

        btnRecipeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recievedIntent = getIntent();
                selectedRecipieFolderID = recievedIntent.getIntExtra("FolderID", -1);
                Log.d(TAG, "recipie folder id value is: " + selectedRecipieFolderID);

                String recipieName = editRecipieText.getText().toString();
                if (editRecipieText.length() != 0) {
                    insertItem(recipieName, selectedRecipieFolderID);
                    editRecipieText.setText("");
                } else {
                    toastMessage("Please put something in the textbox!");
                }
            }
        });

    }

    public void insertItem(String recipieName, int selectedRecipieFolderID) {

        boolean insertData = mDatabaseHelper.addRecipieData(recipieName, selectedRecipieFolderID);

        if (insertData) {
            toastMessage("Data successfully inserted!");

            Cursor data = mDatabaseHelper.getRecipieItemID(recipieName);
            int itemID = -1;
            while (data.moveToNext()) {
                itemID = data.getInt(0);
            }
            toastMessage("The recipieID is: " + itemID);

//            Intent intent = new Intent(RecipieInsert.this, IngredientScreen.class);
            Intent intent = new Intent(RecipieInsert.this, IngredientLayoutScreen.class);
            intent.putExtra("RecipieName", recipieName);
            intent.putExtra("RecipieId", itemID);
                     // receivedIntent.getIntExtra("RecipieId", -1);
            startActivity(intent);
        } else {
            toastMessage("Something went wrong!");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();



    }
}
