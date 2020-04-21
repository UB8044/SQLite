package com.example.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "DBPerson.db";
    public static final String MHS_COLUMN_ID = "id";
    public static final String PRS_TABLE_NAME = "person";
    public static final String PRS_COLUMN_NAMA = "nama";
    public static final String PRS_COLUMN_TELEPON = "nomor_telepon";
    public static final String PRS_COLUMN_EMAIL = "email";
    public static final String PRS_COLUMN_ALAMAT = "alamat";
    private HashMap hp;

    public Database(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table person " +
                        "(id integer primary key, nama text, nomor_telepon text, email text, alamat text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }

    public boolean insertContact(String nama, String nomor_telepon, String email, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("nomor_telepon", nomor_telepon);
        contentValues.put("email", email);
        contentValues.put("alamat", alamat);

        db.insert("person", null, contentValues);
        return true;
    }

    public void deleteContact(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String filter = "id=" + id;
        db.delete("person", filter, null);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from person where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PRS_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from person", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(PRS_COLUMN_NAMA)));
            res.moveToNext();
        }
        return array_list;
    }
}
