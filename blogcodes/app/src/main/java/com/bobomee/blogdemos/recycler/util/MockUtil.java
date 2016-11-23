package com.bobomee.blogdemos.recycler.util;

import android.graphics.Color;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on 16/7/22.下午9:45.
 *
 * @author bobomee.
 * @description:
 */
public class MockUtil {

  public static int getRadomColor() {
    Random rnd = new Random();
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
  }

  public static int getRadomRange(int min, int max) {
    Random random = new Random();
    return random.nextInt(max) % (max - min + 1) + min;
  }

  public static List<Model> getRadomList() {

    List<Model> list = new ArrayList<>();

    for (int i = 0; i < getRadomRange(50, 100); ++i) {

      list.add(
          new Model(getRadomColor(), new Pair<>(getRadomRange(200, 300), getRadomRange(200, 300)),
              i + ""));
    }

    return list;
  }

  public static class Model {
    public String mString;

    public int mColor;

    public Pair<Integer, Integer> mLp;

    public Model(int color, Pair<Integer, Integer> lp, String string) {
      mColor = color;
      mLp = lp;
      mString = string;
    }
  }
}
