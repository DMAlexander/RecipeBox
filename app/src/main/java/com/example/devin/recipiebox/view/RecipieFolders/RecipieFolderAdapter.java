package com.example.devin.recipiebox.view.RecipieFolders;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.devin.recipiebox.R;

import java.util.ArrayList;

public class RecipieFolderAdapter extends RecyclerView.Adapter<RecipieFolderAdapter.RecipieFolderViewHolder> {
    private ArrayList<RecipieFolderItem> mRecipeFolderList;
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    private  static final String TAG = "RecipieFolderAdapter";

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

        public RecipieFolderViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);

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

//        final String spinner1Value = mCursor.getString(mCursor.getColumnIndex("Quantity"));
//        holder.mTextView1.setText(spinner1Value);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
