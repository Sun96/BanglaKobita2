package com.foxappsbd.banglakobita;

/**
 * Created by Arafat on 7/26/2016.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amit Saha on 9/20/2015.
 */
public class PersonDatabase extends SQLiteOpenHelper {

    public static final String Database_NAME = "Member.db";
    public static final String Table_NAME =  "Member_Table";
    public static final String Col_1 =  "ID";
    public static final String Col_2 =  "NAME";
    public static final String Col_3 =  "SURNAME";

    public PersonDatabase(Context context) {
        super(context, Database_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+Table_NAME);
        onCreate(db);
    }

    public boolean insertdata(String name, String surname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2,name);
        contentValues.put(Col_3,surname);
        long result= db.insert(Table_NAME, null, contentValues);
        if (result == -1)

            return false;

        else
            return true;
    }

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from "+Table_NAME,null);
        return res;

    }

    public Boolean updateuserData(String id, String name, String surname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1,id);
        contentValues.put(Col_2,name);
        contentValues.put(Col_3,surname);
        db.update(Table_NAME , contentValues , "ID = ?",new String[] { id });
        return true;

    }
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_NAME, "ID = ?",new String[]{id} );


    }
}

