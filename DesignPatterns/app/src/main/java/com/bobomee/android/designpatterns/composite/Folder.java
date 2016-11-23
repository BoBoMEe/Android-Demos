package com.bobomee.android.designpatterns.composite;

import android.util.Log;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 16/8/28.上午11:31.
 *
 * @author bobomee.
 * @description:
 */
public class Folder extends Dir {
  private static final String TAG = "Folder";

  public Folder(String _name) {
    super(_name);
  }

  @Override public void addDir(Dir _dir) {
    mDirs.add(_dir);
  }

  @Override public void rmDir(Dir _dir) {
    mDirs.remove(_dir);
  }

  @Override public void clear() {
    mDirs.clear();
  }

  @Override public void print() {
    Log.d(TAG, "print: --->" + getName() + "(");
    Iterator<Dir> iter = mDirs.iterator();
    while (iter.hasNext()) {
      Dir dir = iter.next();
      dir.print();
      if (iter.hasNext()) {
        Log.d(TAG, "print: -->" + ",");
      }
    }
    Log.d(TAG, "print: -->" + ")");
  }

  @Override public List<Dir> getFiles() {
    return mDirs;
  }
}
