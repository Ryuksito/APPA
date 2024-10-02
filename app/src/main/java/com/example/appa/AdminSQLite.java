package com.example.appa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLite extends SQLiteOpenHelper {
    public AdminSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "author_lastname TEXT, " +
                "author_initials TEXT, " +
                "year_of_publication INTEGER, " +
                "title TEXT, " +
                "edition INTEGER, " +
                "place_of_publication TEXT, " +
                "publisher TEXT)");

        db.execSQL("CREATE TABLE journals (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "author_lastname TEXT, " +
                "author_initials TEXT, " +
                "publication_date TEXT, " +
                "title TEXT, " +
                "volume INTEGER, " +
                "issue_number INTEGER, " +
                "pages TEXT)");

        db.execSQL("CREATE TABLE webs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "author_lastname TEXT, " +
                "author_initials TEXT, " +
                "publication_date TEXT, " +
                "title TEXT, " +
                "consultation_date TEXT, " +
                "url TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");
        db.execSQL("DROP TABLE IF EXISTS journals");
        db.execSQL("DROP TABLE IF EXISTS webs");

        onCreate(db);
    }
}
