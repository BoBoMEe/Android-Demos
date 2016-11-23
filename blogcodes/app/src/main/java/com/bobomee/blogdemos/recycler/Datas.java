package com.bobomee.blogdemos.recycler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/7/23.下午3:03.
 *
 * @author bobomee.
 * @description:
 */
public class Datas {

  public static class Item {
    public final Class<?> activityClass;
    public final int title;

    public Item(Class<?> activityClass, int title) {
      this.activityClass = activityClass;
      this.title = title;
    }
  }

  public static List<String> getDatas() {
    List<String> mDatas = new ArrayList<String>();
    for (int i = 'A'; i < 'z'; i++) {
      mDatas.add(String.valueOf((char) i));
    }
    return mDatas;
  }
}
