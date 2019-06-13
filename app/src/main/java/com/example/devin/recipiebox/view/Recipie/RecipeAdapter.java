package com.example.devin.recipiebox.view.Recipie;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<RecipeItem> mRecipeList;
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
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
                        System.out.println("Itemview: " + itemView);
             //           int position = getItemViewType();
             //           int position = getItemId();
             //           int position = itemView;
//                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //This tells us the mDeleteImage can be deleted...
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
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
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String recipieName = mCursor.getString(mCursor.getColumnIndex("RecipieName"));
    //    RecipeItem currentItem = mRecipeList.get(position);
 //       holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView1.setText(recipieName);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    /*
    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
    /*

    //Custom method to get item (was done by default with listView...)
    /*
    public ArrayList<RecipeItem> getItem(int position) {
        return mRecipeList.get(position);
    }
    */
}
