package com.bobomee.android.providertutorial.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bobomee.android.providertutorial.R;
import com.bobomee.android.providertutorial.db.Todo;
import com.bobomee.android.providertutorial.provider.TodoProvider;

/**
 * Created on 16/7/7.下午10:42.
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
/*
 * TodoDetailActivity allows to enter a new todo item
 * or to change an existing
 */
public class TodoDetailActivity extends Activity {
  private EditText mTitleText;
  private EditText mBodyText;

  private Uri todoUri;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.todo_edit);

    mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
    mBodyText = (EditText) findViewById(R.id.todo_edit_description);
    Button confirmButton = (Button) findViewById(R.id.todo_edit_button);

    Bundle extras = getIntent().getExtras();

    // check from the saved Instance
    todoUri = (bundle == null) ? null : (Uri) bundle
        .getParcelable(TodoProvider.TODO);

    // Or passed from the other activity
    if (extras != null) {
      todoUri = extras
          .getParcelable(TodoProvider.TODO);

      fillData(todoUri);
    }

    confirmButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        if (TextUtils.isEmpty(mTitleText.getText().toString())) {
          makeToast();
        } else {
          setResult(RESULT_OK);
          finish();
        }
      }

    });
  }

  private void fillData(Uri uri) {
    String[] projection = { Todo.COLUMN_SUMMARY,
        Todo.COLUMN_DESCRIPTION };
    Cursor cursor = getContentResolver().query(uri, projection, null, null,
        null);
    if (cursor != null) {
      cursor.moveToFirst();

      mTitleText.setText(cursor.getString(cursor
          .getColumnIndexOrThrow(Todo.COLUMN_SUMMARY)));
      mBodyText.setText(cursor.getString(cursor
          .getColumnIndexOrThrow(Todo.COLUMN_DESCRIPTION)));

      // always close the cursor
      cursor.close();
    }
  }

  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    saveState();
    outState.putParcelable(TodoProvider.TODO, todoUri);
  }

  @Override
  protected void onPause() {
    super.onPause();
    saveState();
  }

  private void saveState() {
    String summary = mTitleText.getText().toString();
    String description = mBodyText.getText().toString();

    // only save if either summary or description
    // is available

    if (description.length() == 0 && summary.length() == 0) {
      return;
    }

    ContentValues values = new ContentValues();
    values.put(Todo.COLUMN_SUMMARY, summary);
    values.put(Todo.COLUMN_DESCRIPTION, description);

    if (todoUri == null) {
      // New todo
      todoUri = getContentResolver().insert(
          TodoProvider.CONTENT_URI, values);
    } else {
      // Update todo
      getContentResolver().update(todoUri, values, null, null);
    }
  }

  private void makeToast() {
    Toast.makeText(TodoDetailActivity.this, "Please maintain a summary",
        Toast.LENGTH_LONG).show();
  }
}