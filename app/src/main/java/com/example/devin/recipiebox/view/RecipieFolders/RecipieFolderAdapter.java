package com.example.devin.recipiebox.view.RecipieFolders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Recipie.MainActivity;

import java.io.File;
import java.util.ArrayList;

public class RecipieFolderAdapter extends RecyclerView.Adapter<RecipieFolderAdapter.RecipieFolderViewHolder> {
    private ArrayList<RecipieFolderItem> mRecipeFolderList;
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;
    private DatabaseHelper mDatabaseHelper;

    private  static final String TAG = "RecipieFolderAdapter";
    private static final String IMAGE_DIRECTORY = "/demonuts";

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }

    public RecipieFolderAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public static class RecipieFolderViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public CardView mCardView;
        public ImageView mImageView;

        public RecipieFolderViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mCardView = itemView.findViewById(R.id.cardView);
            mImageView = itemView.findViewById(R.id.iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        System.out.println("position: " + position);
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecipieFolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_recipe_folder_item, parent, false);
        return new RecipieFolderViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(RecipieFolderViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        final int recipieFolderID = mCursor.getInt(mCursor.getColumnIndex("FolderID"));
 //       final int recipieFolderPicID = recipieFolderID + 1;
 //       int fss = mDatabaseHelper.getRecipeFolderTableCount();
        final String recipieFolderName = mCursor.getString(mCursor.getColumnIndex("RecipieFolderName"));
        holder.mTextView1.setText(recipieFolderName);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mCursor.getPosition());
                Log.d(TAG, "onClick: recipieFolderID is: " + recipieFolderID);
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("FolderID", recipieFolderID);
                intent.putExtra("RecipieFolderName", recipieFolderName);
                mContext.startActivity(intent);
            }
        });

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        String fileName = "/myImage" + recipieFolderID;
        //    File imgFile = new File("/storage/emulated/0/demonuts/" + fileName + ".jpg");
        File imgFile = new File(wallpaperDirectory + fileName + ".jpg");
        if(imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.mImageView.setImageBitmap(bitmap);
        }

        /*
        holder.mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mCursor.getPosition());
                Log.d(TAG, "onClick: recipieFolderID is: " + recipieFolderID);
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("FolderID", recipieFolderID);
                intent.putExtra("RecipieFolderName", recipieFolderName);
                mContext.startActivity(intent);
            }
        });
        */

//        final String spinner1Value = mCursor.getString(mCursor.getColumnIndex("Quantity"));
//        holder.mTextView1.setText(spinner1Value);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
