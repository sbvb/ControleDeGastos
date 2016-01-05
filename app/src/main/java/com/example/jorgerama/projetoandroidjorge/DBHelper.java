package com.example.jorgerama.projetoandroidjorge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jorge Rama on 04/01/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String USER_CREATE_TABLE =
            "CREATE TABLE " + "users" + " (" +
                    " name TEXT, " + " email TEXT," +
                    " password TEXT);";

    DBHelper(Context context) {
        super(context, "DBAuxilio", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(USER_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}