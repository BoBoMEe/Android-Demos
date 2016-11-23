package com.bobomee.android.designpatterns.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/8/8.上午12:11.
 *
 * @author bobomee.
 * @description:
 */
public class CloneImpl2 implements Cloneable {

  private int mInt;

  private ArrayList mList;

  @Override protected Object clone() throws CloneNotSupportedException {
    CloneImpl2 clone = (CloneImpl2) super.clone();
    clone.mList = (ArrayList) mList.clone();

    return clone;
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
    return "CloneImpl2{" +
        "mInt=" + mInt +
        ", mList=" + mList +
        '}';
  }
}
