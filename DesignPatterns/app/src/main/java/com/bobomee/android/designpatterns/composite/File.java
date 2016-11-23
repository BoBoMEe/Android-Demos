package com.bobomee.android.designpatterns.composite;

import android.util.Log;
import java.util.List;

/**
 * Created on 16/8/28.上午11:35.
 *
 * @author bobomee.
 * @description:
 */
public class File extends Dir {
  private static final String TAG = "File";
  public File(String _name) {
    super(_name);
  }

  @Override public void addDir(Dir _dir) {
    throw new UnsupportedOperationException("文件对象不支持该操作");
  }

  @Override public void rmDir(Dir _dir) {
    throw new UnsupportedOperationException("文件对象不支持该操作");
  }

  @Override public void clear() {
    throw new UnsupportedOperationException("文件对象不支持该操作");
  }

  @Override public void print() {
    Log.d(TAG, "print: -->"+getName());
  }

  @Override public List<Dir> getFiles() {
    throw new UnsupportedOperationException("文件对象不支持该操作");
  }
}
