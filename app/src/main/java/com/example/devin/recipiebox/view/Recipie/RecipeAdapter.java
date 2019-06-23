package com.example.devin.recipiebox.view.Recipie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Ingredient.IngredientScreen;
import com.example.devin.recipiebox.view.PublishedIngredient.IngredientInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<RecipeItem> mRecipeList;
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    private static final String TAG = "RecipieAdapter";

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position, String recipeName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecipeAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public ImageView mDeleteImage;
        public RelativeLayout relativeLayout;

        public RecipeViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        System.out.println("position: " + position + "getItemViewType: " + getItemViewType());
                        //           int position = getItemViewType();
                        //           int position = getItemId();
                        //           int position = itemView;

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                            //            }
                            //          int position = getLayoutPosition();
                            //            mCursor.moveToPosition(position);
                            //            mContext.startActivity(new Intent(mContext, IngredientScreen.class));
                            //The above is me attemtping to navigate to the next view using an intent...
                            //If this works, I should also try to pass some data...
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //This tells us the mDeleteImage can be deleted...
                    if (listener != null) {
                        final int position = getAdapterPosition();
                        String recipeName = mTextView1.getText().toString();
                        Log.d(TAG, "recipie Name is: " + recipeName);
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position, recipeName);
                        }
                    }
                }
            });
        }
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_recipe_item, parent, false);
        return new RecipeViewHolder(view, mListener);
 //       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recipe_item, parent, false);
 //       RecipeViewHolder rvh = new RecipeViewHolder(v, mListener);
 //       return rvh;

    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        final String recipieName = mCursor.getString(mCursor.getColumnIndex("RecipieName"));
    //    RecipeItem currentItem = mRecipeList.get(position);
 //       holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView1.setText(recipieName);
 //       holder.mTextView1.setText(mCursor.getPosition());
//        holder.mTextView1.setText(String.valueOf(mCursor.getPosition()));
//        holder.mTextView1.setText(mCursor.getColumnIndex("ID"));
        Log.d(TAG, "Recipie Name value is: " + recipieName);

        holder.mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mCursor.getPosition());

     //           Intent intent = new Intent(mContext, IngredientScreen.class);
      //          Intent intent = new Intent(mContext, IngredientScreen.class);
                Intent intent = new Intent(mContext, IngredientInfo.class); //we're re-routing to IngredientInfo instead...
                intent.putExtra("RecipieName", recipieName);
                mContext.startActivity(intent);

            }

        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /*
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
    */
}
