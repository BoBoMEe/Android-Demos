package com.bobomee.blogdemos.recycler.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.recycler.BaseRecycler;
import com.bobomee.blogdemos.recycler.Datas;
import com.bobomee.blogdemos.recycler.decoration.MarginDecoration;
import com.bobomee.blogdemos.recycler.grid.GridAddItemRecycler;
import com.bobomee.blogdemos.recycler.grid.GridLayoutManagerRecycler;
import com.bobomee.blogdemos.recycler.grid.GridSpanSizeRecycler;
import com.bobomee.blogdemos.recycler.grid.GridWithHeaderRecycler;
import com.bobomee.blogdemos.recycler.helper.RvItemClickListener;
import com.bobomee.blogdemos.recycler.linear.LinearLayoutManagerRecycler;
import com.bobomee.blogdemos.recycler.staggered.StaggeredRecycler;
import com.bobomee.blogdemos.recycler.staggered.StaggeredSelectRecycler;

/**
 * Created on 16/7/23.下午2:59.
 *
 * @author bobomee.
 * @description:
 */
public class RecyclerViewActivity extends BaseRecycler {

  private Datas.Item[] mItems;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initDatas();

    initViews();
  }

  private void initDatas() {
    mItems = new Datas.Item[] {
        new Datas.Item(LinearLayoutManagerRecycler.class, R.string.linear),
        new Datas.Item(GridLayoutManagerRecycler.class, R.string.grid),
        new Datas.Item(StaggeredRecycler.class, R.string.staggered),
        new Datas.Item(GridSpanSizeRecycler.class, R.string.spansize),
        new Datas.Item(GridWithHeaderRecycler.class, R.string.gridwithheadr),
        new Datas.Item(GridAddItemRecycler.class, R.string.gridaddremove),
        new Datas.Item(StaggeredSelectRecycler.class, R.string.gridselect),
    };
  }

  private void initViews() {

    mRecyclerView.addItemDecoration(new MarginDecoration(this));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    mRecyclerView.setAdapter(new RecyclerMainAdapter(mItems));

    mRecyclerView.addOnItemTouchListener(new RvItemClickListener(mRecyclerView) {
      @Override public void onItemClick(RecyclerView.ViewHolder vh) {
        startActivity(
            new Intent(RecyclerViewActivity.this, mItems[vh.getAdapterPosition()].activityClass));
      }

      @Override public void onItemLongClick(RecyclerView.ViewHolder vh) {

      }
    });
  }
}
