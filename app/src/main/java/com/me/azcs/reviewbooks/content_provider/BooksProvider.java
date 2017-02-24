package com.me.azcs.reviewbooks.content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.me.azcs.reviewbooks.content_provider.database.FavoratedBooksDatabase;

import static com.me.azcs.reviewbooks.Constant.BOOKS_TABLE_NAME;
import static com.me.azcs.reviewbooks.Constant.BOOK_ID;
import static com.me.azcs.reviewbooks.Constant.BOOK_NAME;
import static com.me.azcs.reviewbooks.Constant.CONTENT_URI;
import static com.me.azcs.reviewbooks.Constant.ITEM;
import static com.me.azcs.reviewbooks.Constant.ITEM_PATH;
import static com.me.azcs.reviewbooks.Constant.LIST;
import static com.me.azcs.reviewbooks.Constant.LIST_PATH;
import static com.me.azcs.reviewbooks.Constant.PATH;
import static com.me.azcs.reviewbooks.Constant.ProviderName;
import static com.me.azcs.reviewbooks.Constant._ID;

/**
 * Created by azcs on 16/02/17.
 */

public class BooksProvider extends ContentProvider {
    public static UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ProviderName,PATH+"/#",ITEM);
        URI_MATCHER.addURI(ProviderName,PATH,LIST);
    }
    FavoratedBooksDatabase mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new FavoratedBooksDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables(BOOKS_TABLE_NAME);
            switch (URI_MATCHER.match(uri)) {
                case LIST:
                    //qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                    break;

                case ITEM:
                    qb.appendWhere( _ID + "=" + uri.getPathSegments());
                    break;

                default:
            }

            if (sortOrder == null || sortOrder == ""){
                /**
                 * By default sort on Book names
                 */
                sortOrder = BOOK_NAME;
            }

            Cursor c = qb.query(db,	projection,	selection,
                    selectionArgs,null, null, sortOrder);
            /**
             * register to watch a content URI for changes
             */
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)){
            case ITEM:
                return ITEM_PATH;

            case LIST:
                return LIST_PATH;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        /**
         * Add a new book record
         */
        long rowID = db.insert(	BOOKS_TABLE_NAME, "", values);
        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int count ;
        switch (URI_MATCHER.match(uri)){
            case ITEM:
                count = db.delete(BOOKS_TABLE_NAME, BOOK_ID + "= '" + selection+" '", selectionArgs);
                break;

            case LIST:
                String id = uri.getPathSegments().get(1);
                count = db.delete( BOOKS_TABLE_NAME, _ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND ("
                                        + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int count ;
            switch (URI_MATCHER.match(uri)) {
                case LIST:
                    count = db.update(BOOKS_TABLE_NAME, values, selection, selectionArgs);
                    break;

                case ITEM:
                    count = db.update(BOOKS_TABLE_NAME, values,
                            _ID + " = " + uri.getPathSegments().get(1) +
                                    (!TextUtils.isEmpty(selection) ? " AND ("
                                            +selection + ')' : ""), selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri );
            }

            getContext().getContentResolver().notifyChange(uri, null);
            return count;
    }



    public boolean isItemExists(String id, Context context) {
        mDatabaseHelper = new FavoratedBooksDatabase(context);
        final SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "+BOOKS_TABLE_NAME+" where "+BOOK_ID+"='"+id+"'",null);
        if (cursor.getCount() == 0)
            return false;

        return true;
    }
}
