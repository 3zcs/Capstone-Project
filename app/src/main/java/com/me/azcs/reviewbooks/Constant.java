package com.me.azcs.reviewbooks;

import android.net.Uri;

/**
 * Created by azcs on 16/02/17.
 */

public final class Constant {
    public static final String DATA = "data";

    // Content Provider
    public static final String ProviderName = "com.me.azcs.reviewbooks.Books";
    public static final String PATH = "/books";
    public static final String URL = "content://"+ProviderName+PATH;
    public static final String URL_ITEM = "content://"+ProviderName+PATH+"/1";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    public static final Uri CONTENT_URI_ITEM = Uri.parse(URL_ITEM);


    public static final int ITEM = 1;
    public static final int LIST = 2;

    public static final String ITEM_PATH = "vnd.android.cursor.item/vnd.reviewbooks.list";
    public static final String LIST_PATH = "vnd.android.cursor.dir/vnd.reviewbook.list";


    // Database
    public static final String DATABASE_NAME = "BooksReview";
    public static final String BOOKS_TABLE_NAME = "books";
    public static final int DATABASE_VERSION = 2;

    public static final String _ID = "_id";
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_NAME = "book_name";
    public static final String BOOK_AUTHOR = "book_author";
    public static final String BOOK_COVER = "book_cover";

    public static final String CREATE_DB_TABLE =
            " CREATE TABLE " + BOOKS_TABLE_NAME + " (" +
                    _ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BOOK_ID + " INTEGER NOT NULL, " +
                    BOOK_NAME + " TEXT NOT NULL, " +
                    BOOK_AUTHOR + " TEXT, " +
                    BOOK_COVER + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

}
