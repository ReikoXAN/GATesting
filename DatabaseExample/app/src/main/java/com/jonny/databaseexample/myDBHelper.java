package com.jonny.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

/**
 * Created by reiko_000 on 30/01/2016.
 */
public class myDBHelper extends SQLiteOpenHelper{

    private  static final int DatabaseVersion = 1;
    private  static  final String DATABASE_NAME = "seedex.db";
    private static final String TABLE_SEED = "seeds";
    private static final String COLUMN_ID = "_id";
    private static final String COULUMN_SEEDNAME = "seedname";

    private static final String TABLE_IMAGE = "imagetest";
    private static final String COULUMN_IMGID = "_id";
    private static final String COULUMN_IMAGE = "image";

    /*private static final String TABLE_SEEDTEST = "seedtest2";
    private static final String COLUMN_STESTID = "_id";
    private static final String COULUMN_TESTNAME = "testname";*/

    public myDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  " + TABLE_SEED + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, seedname TEXT)");
        db.execSQL("create table  " + TABLE_IMAGE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOB)");
       // db.execSQL("create table  " + TABLE_SEEDTEST + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, testname TEXT)");

        /*String query = "CREATE TABLE " + TABLE_SEED + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COULUMN_SEEDNAME + " TEXT " +
                ");";
        db.execSQL(query);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SEED);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_IMAGE);
       // db.execSQL("DROP TABLE IF EXISTS" + TABLE_SEEDTEST);
        onCreate(db);
    }

    public void addSeed(Seed seed){
        ContentValues values = new ContentValues();
        values.put(COULUMN_SEEDNAME, seed.get_seedname());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SEED, null, values);
        db.close();
    }

    public void DeleteSeed(String seedname){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SEED + " WHERE " +
                COULUMN_SEEDNAME + "=\"" + seedname + "\";");
    }

    public boolean insertData(byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COULUMN_IMAGE, image);
        long result = db.insert(TABLE_IMAGE,null,cv);
        if(result == -1){
            return  false;
        }
        else{
            return true;
        }
    }
}
