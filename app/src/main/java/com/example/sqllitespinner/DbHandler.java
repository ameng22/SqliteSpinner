package com.example.sqllitespinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int dbVersion = 1;
    private static final String dbName = "spinnerDB";
    private static final String tableName = "label";
    private static final String columnId = "id";
    private static final String columnName = "name";
    public DbHandler(Context context) {
        super(context,dbName,null,dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + tableName + "("
                + columnId + " INTEGER PRIMARY KEY," + columnName + " TEXT)";
        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);

        onCreate(db);
    }

    public void insertLabel(String label){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName,label);
        db.insert(tableName,null,contentValues);
        db.close();
    }

    public List<String> getLables(){
        List<String> labelList = new ArrayList<String>();


        String selectQuery = "SELECT * FROM "+ tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                labelList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labelList;
    }
}
