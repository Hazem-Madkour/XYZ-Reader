package com.example.xyzreader.adapters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;
import com.example.xyzreader.holders.ArticleHolder;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleHolder> {
    private Cursor mCursor;
    private Activity mActivity;

    public ArticleListAdapter(Activity activity, Cursor cursor) {
        mActivity = activity;
        mCursor = cursor;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ArticleLoader.Query._ID);
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.list_item_article, parent, false);
        final ArticleHolder vh = new ArticleHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                        ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition()))));
            }
        });
        return vh;
    }


    @Override
    public void onBindViewHolder(final ArticleHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.fillViews(mCursor);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
