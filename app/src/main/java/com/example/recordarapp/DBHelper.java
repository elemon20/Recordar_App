package com.example.recordarapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {

   public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(rut TEXT primary key, username TEXT, password TEXT, respuesta TEXT)");
        MyDB.execSQL("create Table events(id INTEGER PRIMARY KEY, nombre TEXT, fecha TEXT, importancia TEXT, observacion TEXT, lugar TEXT, user_rut TEXT, FOREIGN KEY(user_rut) REFERENCES users(rut))");
        Log.d("TAG_", "Crea Modelo Relacional");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        //MyDB.execSQL("drop Table if exists users");
    }




}
