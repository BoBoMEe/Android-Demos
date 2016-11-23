package com.bobomee.blogdemos.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.recycler.RecyclerViewholder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created on 16/7/24.下午8:08.
 *
 * @author bobomee.
 * @description:
 */
public abstract class BaseRecyclerAdapter extends SelectableAdapter<RecyclerViewholder> {

  public List<String> datas;

  public BaseRecyclerAdapter(List<String> datas) {
    this.datas = datas;
  }

  public void add() {
    int size = datas.size();
    datas.add(0,++size + "");
    notifyItemInserted(0);
  }

  public void removeItem(int position) {
    datas.remove(position);
    notifyItemRemoved(position);
  }

  @Override public RecyclerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

    return new RecyclerViewholder(view);
  }

  public void swap(int firstPosition, int secondPosition) {
    Collections.swap(datas, firstPosition, secondPosition);
    notifyItemMoved(firstPosition, secondPosition);
  }

  public void removeItems(List<Integer> positions) {
    // Reverse-sort the list
    Collections.sort(positions, new Comparator<Integer>() {
      @Override public int compare(Integer lhs, Integer rhs) {
        return rhs - lhs;
      }
    });

    // Split the list in ranges
    while (!positions.isEmpty()) {
      if (positions.size() == 1) {
        removeItem(positions.get(0));
        positions.remove(0);
      } else {
        int count = 1;
        while (positions.size() > count && positions.get(count)
            .equals(positions.get(count - 1) - 1)) {
          ++count;
        }

        if (count == 1) {
          removeItem(positions.get(0));
        } else {
          removeRange(positions.get(count - 1), count);
        }

        for (int i = 0; i < count; ++i) {
          positions.remove(0);
        }
      }
    }
  }

  private void removeRange(int positionStart, int itemCount) {
    for (int i = 0; i < itemCount; ++i) {
      datas.remove(positionStart);
    }
    notifyItemRangeRemoved(positionStart, itemCount);
  }

  @Override public int getItemCount() {
    return datas.size();
  }
}
