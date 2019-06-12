package com.example.devin.recipiebox.view.Recipie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.devin.recipiebox.R;

public class RecipeItem {

//    private int mImageResource;
    private String mText1;
//    private String mText2;

    public RecipeItem(/*int imageResource,*/ String text1/*, String text2*/) {
   //     mImageResource = imageResource;
        mText1 = text1;
   //     mText2 = text2;
    }
/*
    public void changeText1(String text) {
        mText1 = text;
    }
*/ /*
    public int getImageResource() {
        return mImageResource;
    }
*/
    public String getText1() {
        return mText1;
    }
/*
    public String getText2() {
        return mText2;
    }
    */
}
