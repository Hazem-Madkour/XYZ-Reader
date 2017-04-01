package com.example.xyzreader.holders;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class ArticleHolder extends RecyclerView.ViewHolder {

    static private final GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2, 1, 1);

    public ImageView thumbnailView;
    private TextView titleView;
    private TextView subtitleView;

    public ArticleHolder(View view) {
        super(view);
        thumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
        titleView = (TextView) view.findViewById(R.id.article_title);
        subtitleView = (TextView) view.findViewById(R.id.article_subtitle);
        titleView.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(), "Rosario-Regular.ttf"));
        subtitleView.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(), "Rosario-Regular.ttf"));
    }

    public void fillViews(final Cursor cursor) {
        titleView.setText(cursor.getString(ArticleLoader.Query.TITLE));
        Date publishedDate = parsePublishedDate(cursor);
        if (!publishedDate.before(START_OF_EPOCH.getTime())) {
            subtitleView.setText(Html.fromHtml(
                    DateUtils.getRelativeTimeSpanString(
                            publishedDate.getTime(),
                            System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_ALL).toString()
                            + "<br/>" + " by "
                            + cursor.getString(ArticleLoader.Query.AUTHOR)));
        } else {
            subtitleView.setText(Html.fromHtml(
                    new SimpleDateFormat().format(publishedDate)
                            + "<br/>" + " by "
                            + cursor.getString(ArticleLoader.Query.AUTHOR)));
        }

        Glide.with(itemView.getContext()).load(cursor.getString(ArticleLoader.Query.THUMB_URL)).asBitmap().placeholder(R.drawable.ic_loading).error(R.drawable.img_error_no_img).into(thumbnailView);
    }

    private Date parsePublishedDate(Cursor cursor) {
        try {
            String date = cursor.getString(ArticleLoader.Query.PUBLISHED_DATE);
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss").parse(date);
        } catch (ParseException ex) {
            return new Date();
        }
    }
}
