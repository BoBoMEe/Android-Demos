package com.bobomee.android.designpatterns.interpreter;

/**
 * Created on 16/9/6.下午11:31.
 *
 * @author bobomee.
 * @description:
 */
public abstract class OperatorExpression extends ArithmeticExpression {
  protected ArithmeticExpression mArithmeticExpression1,mArithmeticExpression2;

  public OperatorExpression(ArithmeticExpression _arithmeticExpression1,
      ArithmeticExpression _arithmeticExpression2) {
    mArithmeticExpression1 = _arithmeticExpression1;
    mArithmeticExpression2 = _arithmeticExpression2;
  }
}
