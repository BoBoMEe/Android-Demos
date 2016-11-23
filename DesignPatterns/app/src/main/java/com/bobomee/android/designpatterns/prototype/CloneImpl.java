package com.bobomee.android.designpatterns.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/8/8.上午12:11.
 *
 * @author bobomee.
 * @description:
 */
public class CloneImpl  implements Cloneable {

  private int mInt;

  private ArrayList mList;

  @Override protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public int getInt() {
    return mInt;
  }

  public void setInt(int anInt) {
    mInt = anInt;
  }

  public List getList() {
    return mList;
  }

  public void setList(ArrayList list) {
    mList = list;
  }

  @Override public String toString() {
    return "CloneImpl{" +
        "mInt=" + mInt +
        ", mList=" + mList +
        '}';
  }
}
