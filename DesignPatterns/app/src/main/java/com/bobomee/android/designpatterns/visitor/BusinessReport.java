package com.bobomee.android.designpatterns.visitor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 16/8/27.下午1:23.
 *
 * @author bobomee.
 * @description:
 */
public class BusinessReport {

  List<Staff> mStaffs = new LinkedList<>();

  public BusinessReport() {
    mStaffs.add(new Manager("王经理"));
    mStaffs.add(new Engineer("工程师小刘"));
    mStaffs.add(new Engineer("工程师小张"));
    mStaffs.add(new Engineer("工程师小李"));
  }

  public void showReport(Visitor _visitor) {
    for (Staff staff : mStaffs) {
      staff.accept(_visitor);
    }
  }
}
