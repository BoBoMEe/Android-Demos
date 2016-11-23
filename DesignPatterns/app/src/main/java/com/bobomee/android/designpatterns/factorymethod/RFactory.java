package com.bobomee.android.designpatterns.factorymethod;

/**
 * Created on 16/9/5.下午11:59.
 *
 * @author bobomee.
 * @description:
 */
public abstract class RFactory {

  public abstract <T extends Product> T createProduct(Class<T> clz);
}
