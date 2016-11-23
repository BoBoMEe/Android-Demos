package com.bobomee.android.designpatterns.decorator;

import android.util.Log;

/**
 * Created on 16/8/28.下午6:07.
 *
 * @author bobomee.
 * @description:
 */
public class ExpensiveCloth extends PersonCloth {
  private static final String TAG = "ExpensiveCloth";

  public ExpensiveCloth(Person _person) {
    super(_person);
  }

  private void dressShirt() {
    Log.d(TAG, "dressShirt: -->" + "穿件短袖");
  }

  private void dressLeather() {
    Log.d(TAG, "dressLeather: -->" + "穿件皮衣");
  }

  private void dressJean() {
    Log.d(TAG, "dressJean: -->" + "穿件牛仔裤");
  }

  @Override public void dressed() {
    super.dressed();
    dressShirt();
    dressLeather();
    dressJean();
  }
}
