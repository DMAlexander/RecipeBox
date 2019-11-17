package com.example.devin.recipiebox.view.Recipie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.devin.recipiebox.R;

public class RecipeItem {

    private String mText1;
    private String mText2;

    public RecipeItem( String text1, String text2 ) {
        mText1 = text1;
        mText2 = text2;
    }

    public void changeText1(String text) {
        mText1 = text;
    }
    public String getText1() {
        return mText1;
    }
    public String getText2() { return mText2; }
}
