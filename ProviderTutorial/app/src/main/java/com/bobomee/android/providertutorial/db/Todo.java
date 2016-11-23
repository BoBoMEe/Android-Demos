package com.bobomee.android.providertutorial.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created on 16/7/8.下午10:29.
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
public class Todo {

  public static final String DB = "todo.db";

  public static final int DB_VERSION = 1;

  public static final String TABLE = "todo";

  public static final String COLUMN_ID = "_id";

  public static final String COLUMN_SUMMARY = "summary";

  public static final String COLUMN_DESCRIPTION = "description";

  // Database creation SQL statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE
      + "("
      + COLUMN_ID + " integer primary key autoincrement, "
      + COLUMN_SUMMARY + " text not null,"
      + COLUMN_DESCRIPTION
      + " text not null"
      + ");";


  public static void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
    Log.w(Todo.class.getName(), "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
    database.execSQL("DROP TABLE IF EXISTS " + TABLE);
    onCreate(database);
  }
}
