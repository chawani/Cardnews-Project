package com.example.NewSeconds;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not exists article ("
                + "_id integer primary key autoincrement,"
                + "title text,"
                + "content1 text,"
                + "content2 text,"
                + "content3 text,"
                + "content4 text,"
                + "content5 text,"
                + "content6 text,"
                + "content7 text,"
                + "date text,"
                + "category integer,"
                + "count integer,"
                + "image text);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists article";

        db.execSQL(sql);
        onCreate(db);
    }
}