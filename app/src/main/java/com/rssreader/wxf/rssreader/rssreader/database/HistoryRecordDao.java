package com.rssreader.wxf.rssreader.rssreader.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/27.
 */
public class HistoryRecordDao {

    private final MySqliteOpenHelper mySqliteOpenHelper;
    private ArrayList<String> stringArrayList;

    public HistoryRecordDao(Context context) {
        mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    public void addHistoryRecord(String url) {
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        db.execSQL("insert into historyRecord(url) values(?)", new Object[]{url});
        db.close();
    }

    public void delHistoryRecord(String url) {
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        db.execSQL("delete from historyRecord where url=?", new Object[]{url});
        db.close();
    }

    public ArrayList<String> queryAllHistoryRecord() {
        stringArrayList = new ArrayList<>();
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select url from historyRecord", null);
        if (null != cursor && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String string = cursor.getString(0);
                stringArrayList.add(string);
            }
            cursor.close();
        }
        db.close();
        return stringArrayList;
    }

    public void delAllHistoryRecord() {
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        db.execSQL("delete from historyRecord");
        db.close();
    }
}
