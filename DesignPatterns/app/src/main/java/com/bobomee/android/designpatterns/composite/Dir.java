package com.bobomee.android.designpatterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/8/28.上午11:26.
 *
 * @author bobomee.
 * @description:
 */
public abstract class Dir {
  /**
   * 声明一个List成员变量存储文件夹下的所有元素
   */
  protected List<Dir>mDirs = new ArrayList<>();

  private String name;

  public Dir(String _name) {
    name = _name;
  }

  public abstract void addDir(Dir _dir);

  public abstract void rmDir(Dir _dir);

  public abstract void clear();

  public abstract void print();

  public abstract List<Dir> getFiles();

  public String getName() {
    return name;
  }
}
