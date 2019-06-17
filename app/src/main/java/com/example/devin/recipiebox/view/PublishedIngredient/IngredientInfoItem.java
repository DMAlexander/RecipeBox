package com.example.devin.recipiebox.view.PublishedIngredient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.devin.recipiebox.R;

public class IngredientInfoItem {
    private String mText1;
    private String mText2;
    private String mText3;

    public IngredientInfoItem(String text1, String text2, String text3) {
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
    }

    public String getmText1() {
        return mText1;
    }
    public String getmText2() {
        return mText2;
    }
    public String getmText3() {
        return mText3;
    }
}
