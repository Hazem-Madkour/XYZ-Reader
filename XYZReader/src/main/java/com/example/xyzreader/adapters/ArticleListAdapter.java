package com.example.xyzreader.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;
import com.example.xyzreader.holders.ArticleHolder;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleHolder> {
    private Cursor mCursor;
    private Activity mActivity;
    private int lastAnimatedPosition = -1;
    private boolean isSdkMore21 = false;
    private Interpolator mInterpolator;

    public ArticleListAdapter(Activity activity, Cursor cursor) {
        mActivity = activity;
        mCursor = cursor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            isSdkMore21 = true;
            mInterpolator = AnimationUtils.loadInterpolator(mActivity, android.R.interpolator.linear_out_slow_in);
        }
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
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (isSdkMore21) {
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity, vh.thumbnailView, mActivity.getString(R.string.article_image_transition));
                    mActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                            ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition()))), transitionActivityOptions.toBundle());
                } else
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
        if (isSdkMore21)
            setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastAnimatedPosition) {
            viewToAnimate.setTranslationY((position + 1) * 1000);
            viewToAnimate.setAlpha(0.85f);
            viewToAnimate.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(mInterpolator)
                    .setDuration(1000L)
                    .start();
            lastAnimatedPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(final ArticleHolder holder) {
        if (isSdkMore21)
            holder.itemView.clearAnimation();
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
