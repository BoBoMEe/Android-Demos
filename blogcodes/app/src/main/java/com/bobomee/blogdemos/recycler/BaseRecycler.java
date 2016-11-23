package com.bobomee.blogdemos.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import com.bobomee.android.common.util.ToastUtil;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;
import com.bobomee.blogdemos.recycler.adapter.BaseRecyclerAdapter;

/**
 * Created on 16/7/23.下午7:16.
 *
 * @author bobomee.
 * @description:
 */
public class BaseRecycler extends BaseActivity {

  @BindView(R.id.recycler_view) public RecyclerView mRecyclerView;

  public BaseRecyclerAdapter mAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.recycler_layout);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.recyclerview_add, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.menu_sort: {
        ToastUtil.show(this, "change orientation");

        onChange();
      }
      break;
      case R.id.menu_add:{
        ToastUtil.show(this, "add item");
        onAdd();
      }

        break;
    }

    return super.onOptionsItemSelected(item);
  }

  public void onAdd() {
  }

  public void onChange() {
  }
}
