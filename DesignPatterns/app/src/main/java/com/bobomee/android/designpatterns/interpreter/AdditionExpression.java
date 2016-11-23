package com.bobomee.android.designpatterns.interpreter;

/**
 * Created on 16/9/6.下午11:32.
 *
 * @author bobomee.
 * @description:
 */
public class AdditionExpression extends OperatorExpression {
  public AdditionExpression(ArithmeticExpression _arithmeticExpression1,
      ArithmeticExpression _arithmeticExpression2) {
    super(_arithmeticExpression1, _arithmeticExpression2);
  }

  @Override public int interptet() {
    return mArithmeticExpression1.interptet() + mArithmeticExpression2.interptet();
  }
}
