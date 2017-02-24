package com.me.azcs.reviewbooks.widget;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RemoteViews;

import com.me.azcs.reviewbooks.R;
import com.me.azcs.reviewbooks.models.ImageLinks;
import com.me.azcs.reviewbooks.models.Item;
import com.me.azcs.reviewbooks.models.VolumeInfo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.me.azcs.reviewbooks.Constant.BOOK_AUTHOR;
import static com.me.azcs.reviewbooks.Constant.BOOK_COVER;
import static com.me.azcs.reviewbooks.Constant.BOOK_ID;
import static com.me.azcs.reviewbooks.Constant.BOOK_NAME;
import static com.me.azcs.reviewbooks.Constant.CONTENT_URI;

/**
 * Created by azcs on 23/02/17.
 */

public class WidgetProvider extends AppWidgetProvider {
    RemoteViews remoteViews;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        Uri Books = CONTENT_URI;
        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);
        Cursor cursor = context.getContentResolver().query(Books,null,null,null,null);


        List<Item> items = new ArrayList();
        while(cursor.moveToNext()){
            items.add(addNewItem(
                    cursor.getString(cursor.getColumnIndex(BOOK_ID)),
                    cursor.getString(cursor.getColumnIndex(BOOK_NAME)),
                    cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)),
                    cursor.getString(cursor.getColumnIndex(BOOK_COVER))
            ));
        }

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

        if (items.isEmpty()) {
            remoteViews.setViewVisibility(R.id.book_container, View.GONE);
            remoteViews.setViewVisibility(R.id.no_books, View.VISIBLE);
        }else {
            VolumeInfo info = items.get(new Random().nextInt(items.size())).getVolumeInfo();

            remoteViews.setViewVisibility(R.id.book_container, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.no_books, View.GONE);
            remoteViews.setTextViewText(R.id.bookName, info.getTitle());
            if (!info.getAuthors().isEmpty())
            remoteViews.setTextViewText(R.id.authorName, info.getAuthors().get(0));
            if (!info.getImageLinks().getThumbnail().isEmpty())
                    Picasso.with(context)
                            .load(info.getImageLinks().getThumbnail())
                            .placeholder(R.drawable.defimage)
                            .into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    remoteViews.setImageViewBitmap(R.id.book_cover,bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });
        }
            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.book_cardview, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private Item addNewItem(String id,String name,String author,String cover) {
        return new Item(id,
                new VolumeInfo(name, author,
                        new ImageLinks(cover)));
    }


}
