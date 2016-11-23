package com.bobomee.android.designpatterns.command;

/**
 * Created on 16/8/19.下午10:37.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteCommand implements Command {

  private Receiver mReceiver;
  @Override public void execute() {
    //调用接收者的方法来执行命令
    mReceiver.doAction();
  }

  public ConcreteCommand(Receiver receiver) {
    mReceiver = receiver;
  }
}
