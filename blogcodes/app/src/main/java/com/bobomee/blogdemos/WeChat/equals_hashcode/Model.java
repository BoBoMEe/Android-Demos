package com.bobomee.blogdemos.WeChat.equals_hashcode;

/**
 * Created by bobomee on 2017/12/2.
 */

import android.os.Build;
import java.util.Objects;

public class Model {
  private String name;
  private double salary;
  private int sex;

  @Override public int hashCode() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      return Objects.hash(name, salary, sex);
    } else {
      return Objects.hashCode(name) + Double.valueOf(salary).hashCode() + Integer.valueOf(sex)
          .hashCode();
    }
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Model model = (Model) o;

    if (Double.compare(model.salary, salary) != 0) return false;
    if (sex != model.sex) return false;
    return name != null ? name.equals(model.name) : model.name == null;
  }
}

