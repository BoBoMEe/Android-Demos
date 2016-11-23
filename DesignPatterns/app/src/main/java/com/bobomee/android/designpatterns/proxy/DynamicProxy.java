package com.bobomee.android.designpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created on 16/8/27.下午10:53.
 *
 * @author bobomee.
 * @description:
 */
public class DynamicProxy implements InvocationHandler {

  private Object mObject;

  public DynamicProxy(Object _object) {
    mObject = _object;
  }

  @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object result = method.invoke(mObject, args);
    return result;
  }
}
