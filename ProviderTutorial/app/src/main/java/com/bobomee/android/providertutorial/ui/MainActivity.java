package com.bobomee.android.providertutorial.ui;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.bobomee.android.providertutorial.R;
import com.bobomee.android.providertutorial.db.Todo;
import com.bobomee.android.providertutorial.provider.TodoProvider;

public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
  private static final int DELETE_ID = Menu.FIRST + 1;
  // private Cursor cursor;
  private SimpleCursorAdapter adapter;

  /** Called when the activity is first created. */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.todo_list);
    this.getListView().setDividerHeight(2);
    fillData();
    registerForContextMenu(getListView());
  }

  // create the menu based on the XML defintion
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  // Reaction to the menu selection
  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.insert:
        createTodo();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case DELETE_ID:
        AdapterView.AdapterContextMenuInfo info =
            (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Uri uri = Uri.parse(TodoProvider.CONTENT_URI + "/" + info.id);
        getContentResolver().delete(uri, null, null);
        fillData();
        return true;
    }
    return super.onContextItemSelected(item);
  }

  private void createTodo() {
    Intent i = new Intent(this, TodoDetailActivity.class);
    startActivity(i);
  }

  // Opens the second activity if an entry is clicked
  @Override protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    Intent i = new Intent(this, TodoDetailActivity.class);
    Uri todoUri = Uri.parse(TodoProvider.CONTENT_URI + "/" + id);
    i.putExtra(TodoProvider.TODO, todoUri);

    startActivity(i);
  }

  private void fillData() {

    // Fields from the database (projection)
    // Must include the _id column for the adapter to work
    String[] from = new String[] { Todo.COLUMN_SUMMARY, Todo.COLUMN_DESCRIPTION };
    // Fields on the UI to which we map
    int[] to = new int[] { R.id.todo_summary, R.id.todo_description };

    getLoaderManager().initLoader(0, null, this);
    adapter = new SimpleCursorAdapter(this, R.layout.todo_row, null, from, to, 0);

    setListAdapter(adapter);
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.add(0, DELETE_ID, 0, R.string.menu_delete);
  }

  // creates a new loader after the initLoader () call
  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String[] projection = { Todo.COLUMN_ID, Todo.COLUMN_SUMMARY, Todo.COLUMN_DESCRIPTION };
    return new CursorLoader(this, TodoProvider.CONTENT_URI, projection, null, null, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    adapter.swapCursor(data);
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
    // data is not available anymore, delete reference
    adapter.swapCursor(null);
  }
}