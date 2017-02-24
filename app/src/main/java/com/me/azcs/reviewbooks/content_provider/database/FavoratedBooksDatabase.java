package com.me.azcs.reviewbooks.content_provider.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.me.azcs.reviewbooks.Constant.CREATE_DB_TABLE;
import static com.me.azcs.reviewbooks.Constant.DATABASE_NAME;
import static com.me.azcs.reviewbooks.Constant.DATABASE_VERSION;
import static com.me.azcs.reviewbooks.Constant.BOOKS_TABLE_NAME;
import static com.me.azcs.reviewbooks.Constant.DROP_TABLE;

/**
 * Created by azcs on 16/02/17.
 */

public class FavoratedBooksDatabase extends SQLiteOpenHelper{

    public FavoratedBooksDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + BOOKS_TABLE_NAME);
        onCreate(db);
    }
}
