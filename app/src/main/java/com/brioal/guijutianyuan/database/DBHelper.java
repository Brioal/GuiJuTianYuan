package com.brioal.guijutianyuan.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private final String CREATE_FIND_TABLE = "create table Find ( _id integer primary key autoincrement , mId , mName , mContent , mPicId , mTime long , mParise integer , mCommit integer , mCollect integer)";
    private final String CREATE_BANNER_TABLE = "create table Banner ( _id integer primary key autoincrement ,mContentId ,  mTip , mUrl , mImageUrl )";
    private final String CREATE_CONTENT_TABLE = "create table Content ( _id integer primary key autoincrement ,mTitle , mDesc , mId , mRead integer ,mParise integer , mUrl  )";
    private final String CREATE_FAVORITE_TABLE = "create table Favorite ( _id integer primary key autoincrement , mContentObjectId )";
    private final String CREATE_TAG_TABLE = "create table Tag ( _id integer primary key autoincrement , mTag )";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FIND_TABLE);
        db.execSQL(CREATE_BANNER_TABLE);
        db.execSQL(CREATE_CONTENT_TABLE);
//        db.execSQL(CREATE_FAVORITE_TABLE);
//        db.execSQL(CREATE_TAG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
