package com.example.learnlanguages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LearnALanguages.db";
    private static final String COLUMN = "languages_pairs";
    private static final String TABLE_NAME = "pairs";
    SQLiteDatabase db;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME +" (id integer primary key autoincrement, "+ COLUMN +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addLanguangePair(String pairOfLanguages){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN, pairOfLanguages);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    void addTable(final String THEMATIC_GROUP_NAME, final String LANGUAGE1, final String LANGUAGE2){
        db.execSQL("CREATE TABLE "+THEMATIC_GROUP_NAME+" (id integer primary key autoincrement, "+LANGUAGE1+", " + ""+LANGUAGE2+")");
    }

    void insertIntoTheTable(String tableName, String value1, String value2, String language1, String language2) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(language1, value1);
        contentValues.put(language2, value2);
        long result = db.insert(tableName, null, contentValues);
    }

    Cursor selectFromTheTable(){
        db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    Cursor selectData(String tableName){
        db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + tableName, null);
        return data;
    }

    List<String> selectTableList(){
        List listOfTables = new ArrayList<>();
        db = this.getWritableDatabase();
        Cursor tableListData = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        tableListData.moveToFirst();
        while (!tableListData.isAfterLast()){
            if(tableListData.getString(0).equals("pairs") || tableListData.getString(0).equals("android_metadata") || tableListData.getString(0).equals("sqlite_sequence"))
                {}
            else
                listOfTables.add(tableListData.getString(0));
            tableListData.moveToNext();
        }
        return listOfTables;
    }

    List selectWordsFromTheTable(String tableName, String language1, String language2){
        List wordList = new ArrayList<>();
        String connection = "";
        Cursor worldListData = db.rawQuery("SELECT "+language1+" FROM "+tableName, null);
        Cursor worldListData1 = db.rawQuery("SELECT "+language2+" FROM "+tableName, null);
        worldListData.moveToFirst(); worldListData1.moveToFirst();
        while (!worldListData.isAfterLast()){
            connection = connection+worldListData.getString(0)+" - "+worldListData1.getString(0);
            wordList.add(connection);
            connection = "";
            worldListData1.moveToNext();
            worldListData.moveToNext();
        }
        return wordList;
    }

    boolean isExist(String tableName, String columnName, String value){
        Cursor selectPair = db.rawQuery("SELECT "+columnName+" from "+tableName+" WHERE "+columnName+" = ?", new String[]{value});
        String c = "";
        selectPair.moveToFirst();
        while (!selectPair.isAfterLast()){
            c = c+selectPair.getString(0);
            selectPair.moveToNext();
        }
        if(c.length()==0)
            return false;
        else
            return true;
    }

    void deleteValue(String tableName, String columnName, String value){
        db.execSQL("DELETE FROM "+ tableName +" WHERE "+columnName+" = ?", new String[]{value});
    }

    void dropTable(String tableName){
        db.execSQL("DROP TABLE "+tableName);
    }
}