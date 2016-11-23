package com.bobomee.android.designpatterns.visitor;

import android.util.Log;

/**
 * Created on 16/8/27.下午1:26.
 *
 * @author bobomee.
 * @description:
 */
public class CEOVisitor implements Visitor {
  private static final String TAG = "CEOVisitor";

  @Override public void visit(Engineer _engineer) {
    Log.d(TAG, "visit: -->" + "工程师 : " + _engineer.name + ",kpi : " + _engineer.kpi);
  }

  @Override public void visit(Manager _manager) {
    Log.d(TAG, "visit: -->"
        + "经理 : "
        + _manager.name
        + ",kpi : "
        + _manager.kpi
        + ",新产品数 : "
        + _manager.getProducts());
  }
}
