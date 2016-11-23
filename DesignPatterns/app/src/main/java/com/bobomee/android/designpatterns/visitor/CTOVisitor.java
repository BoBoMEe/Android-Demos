package com.bobomee.android.designpatterns.visitor;

import android.util.Log;

/**
 * Created on 16/8/27.下午1:31.
 *
 * @author bobomee.
 * @description:
 */
public class CTOVisitor implements Visitor {
  private static final String TAG = "CTOVisitor";

  @Override public void visit(Engineer _engineer) {
    Log.d(TAG, "visit: -->" + "工程师 : " + _engineer.name + ",代码数 : " + _engineer.getCoceLines());
  }

  @Override public void visit(Manager _manager) {
    Log.d(TAG, "visit: -->" + "经理 :" + _manager.name + "产品数量 : " + _manager.getProducts());
  }
}
