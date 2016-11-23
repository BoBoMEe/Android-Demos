package com.bobomee.android.designpatterns.factorymethod;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * Created on 16/9/6.上午12:00.
 *
 * @author bobomee.
 * @description:
 */
public class RConcreteFactory extends RFactory {

  @TargetApi(Build.VERSION_CODES.KITKAT) @Override public <T extends Product> T createProduct(Class<T> clz) {
    Product product = null;
    try {
      product = (Product) Class.forName(clz.getName()).newInstance();
    } catch (IllegalAccessException | InstantiationException | ClassNotFoundException _e) {
      _e.printStackTrace();
    }

    return (T) product;
  }
}
