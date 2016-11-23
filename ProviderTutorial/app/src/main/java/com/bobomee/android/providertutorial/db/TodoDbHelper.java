package com.bobomee.android.providertutorial.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 16/7/8.下午10:37.
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
public class TodoDbHelper extends SQLiteOpenHelper {
  public TodoDbHelper(Context context) {
    super(context, Todo.DB, null, Todo.DB_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    Todo.onCreate(sqLiteDatabase);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    Todo.onUpgrade(sqLiteDatabase, i, i1);
  }
}
