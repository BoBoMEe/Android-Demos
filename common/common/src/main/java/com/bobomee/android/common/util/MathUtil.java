package com.bobomee.android.common.util;

import java.math.BigDecimal;

/**
 * 数学运算
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 *
 *         # https://github.com/WuXiaolong/AndroidUtils/blob/master/library/src/main/java/com/wuxiaolong/androidutils/library/ArithUtil.java
 */
public class MathUtil {
  // 默认除法运算精度
  private static final int DEF_DIV_SCALE = 10;

  /**
   * 加法
   */
  public static double plus(double v1, double v2) {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));

    return b1.add(b2).doubleValue();
  }

  /**
   * 减法
   */
  public static double minus(double v1, double v2) {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));

    return b1.subtract(b2).doubleValue();
  }

  /**
   * 乘法
   */
  public static double mul(double v1, double v2) {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));

    return b1.multiply(b2).doubleValue();
  }

  /**
   * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
   * 小数点以后 10 位，以后的数字四舍五入。
   *
   * @param v1 被除数
   * @param v2 除数
   * @return 两个参数的商
   */
  public static double div(double v1, double v2) {
    return div(v1, v2, DEF_DIV_SCALE);
  }

  /**
   * 提供（相对）精确的除法运算。当发生除不尽的情况时，由 scale 参数指
   * 定精度，以后的数字四舍五入。
   *
   * @param v1 被除数
   * @param v2 除数
   * @param scale 表示表示需要精确到小数点以后几位。
   * @return 两个参数的商
   */
  public static double div(double v1, double v2, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }

    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));

    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
  }

  /**
   * 提供精确的小数位四舍五入处理。
   *
   * @param v 需要四舍五入的数字
   * @param scale 小数点后保留几位
   * @return 四舍五入后的结果
   */
  public static double round(double v, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }

    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal one = new BigDecimal("1");

    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
  }
}
