package com.bobomee.android.androidorm;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.bobomee.android.androidorm.liteorm.LiteOrmTestActivity;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {

  String[] items = {
      "liteOrmTest"
  };

  Class[] cls = {
      LiteOrmTestActivity.class
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setListAdapter(new QuickAdapter<String>(this, android.R.layout.simple_list_item_1,
        new ArrayList<String>(Arrays.asList(items))) {
      @Override protected void convert(BaseAdapterHelper helper, String item) {
        helper.setText(android.R.id.text1, item);
      }
    });
  }

  @Override protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);

    startActivity(new Intent(this, cls[position]));
  }
}
