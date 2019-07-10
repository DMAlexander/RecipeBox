package com.example.devin.recipiebox.view.RecipieFolders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.LinearLayout;

import com.example.devin.recipiebox.R;

public class FolderLayoutScreen extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_layout_screen);
        LinearLayout linearLayout = findViewById(R.id.parent_linear_layout);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
    //    linearLayout.setL


    }
}
