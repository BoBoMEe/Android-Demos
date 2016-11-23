package com.bobomee.android.designpatterns.factorymethod;

/**
 * Created on 16/9/5.下午11:43.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteFactory extends Factory {
  @Override public Product createProduct() {
    return new ConcreteProductA();
    //return new ConcreteProductB();
  }
}
