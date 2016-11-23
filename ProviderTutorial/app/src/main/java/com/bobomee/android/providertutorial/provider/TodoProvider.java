package com.bobomee.android.providertutorial.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.bobomee.android.providertutorial.db.Todo;
import com.bobomee.android.providertutorial.db.TodoDbHelper;

public class TodoProvider extends ContentProvider {

  private static final String AUTHORITY = "com.bobomee.android.todo";

  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Todo.TABLE);

  public static final int TODOS_URI_CODE = 0;
  public static final int TODO_URI_CODE = 1;

  private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  // 关联Uri和Uri_Code
  static {
    sUriMatcher.addURI(AUTHORITY, Todo.TABLE, TODOS_URI_CODE);
    sUriMatcher.addURI(AUTHORITY, Todo.TABLE + "/#", TODO_URI_CODE);
  }

  private Context mContext;
  private SQLiteDatabase mDb;

  public TodoProvider() {
  }

  @Override public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
    int count;

    switch (sUriMatcher.match(uri)) {
      case TODOS_URI_CODE:
        count = mDb.delete(Todo.TABLE, selection, selectionArgs);
        break;

      case TODO_URI_CODE:
        String id = uri.getLastPathSegment();
        count = mDb.delete(Todo.TABLE, Todo.COLUMN_ID + " = " + id +
            (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        break;

      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    if (count > 0) mContext.getContentResolver().notifyChange(uri, null);
    return count;
  }

  //得到todos表中的所有记录
  public static String TODOS = "vnd.android.cursor.dir/vnd.example.todos";
  //得到一个表信息
  public static String TODO = "vnd.android.cursor.item/vnd.example.todos";

  @Override public String getType(@NonNull Uri uri) {
    switch (sUriMatcher.match(uri)) {
      case TODOS_URI_CODE:
        return TODOS;

      case TODO_URI_CODE:
        return TODO;

      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override public Uri insert(@NonNull Uri uri, ContentValues values) {
    switch (sUriMatcher.match(uri)) {
      case TODOS_URI_CODE: {
        /**
         * Add a new student record
         */
        long rowID = mDb.insert(Todo.TABLE, "", values);

        /**
         * If record is added successfully
         */

        if (rowID > 0) {
          Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
          mContext.getContentResolver().notifyChange(_uri, null);
          return _uri;
        }
      }
      default:
        //不能识别uri
        throw new IllegalArgumentException("This is a unKnow Uri" + uri.toString());
    }
  }

  @Override public boolean onCreate() {
    mContext = getContext();
    TodoDbHelper todoDbHelper = new TodoDbHelper(mContext);
    mDb = todoDbHelper.getWritableDatabase();
    return false;
  }

  @Override
  public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    // Uisng SQLiteQueryBuilder instead of query() method
    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

    // Set the table
    queryBuilder.setTables(Todo.TABLE);

    switch (sUriMatcher.match(uri)) {
      case TODOS_URI_CODE:
        break;

      case TODO_URI_CODE:
        queryBuilder.appendWhere(Todo.COLUMN_ID + "=" + uri.getPathSegments().get(1));
        break;

      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    Cursor cursor =
        queryBuilder.query(mDb, projection, selection, selectionArgs, null, null, sortOrder);
    // make sure that potential listeners are getting notified
    cursor.setNotificationUri(mContext.getContentResolver(), uri);

    return cursor;
  }

  @Override
  public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    int count;

    switch (sUriMatcher.match(uri)) {
      case TODOS_URI_CODE:
        count = mDb.update(Todo.TABLE, values, selection, selectionArgs);
        break;

      case TODO_URI_CODE:
        count =
            mDb.update(Todo.TABLE, values, Todo.COLUMN_ID + " = " + uri.getPathSegments().get(1) +
                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        break;

      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
    if (count > 0) mContext.getContentResolver().notifyChange(uri, null);
    return count;
  }
}
