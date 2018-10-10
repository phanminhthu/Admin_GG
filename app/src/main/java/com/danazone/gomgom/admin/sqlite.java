package com.danazone.gomgom.admin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FPTSHOP on 20/09/2016.
 */
public class sqlite extends SQLiteOpenHelper{
    public sqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
public void QueryData(String sql){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(sql);



}
    public Cursor GetData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        return  db.rawQuery(sql,null);

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
