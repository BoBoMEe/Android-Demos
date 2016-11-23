package com.bobomee.android.designpatterns.command;

/**
 * Created on 16/8/19.下午10:39.
 *
 * @author bobomee.
 * @description:
 */
public class Invoker {

  private Command mCommand;//持用一个命令对象

  public Invoker(Command command) {
    mCommand = command;
  }

  public void action(){
    mCommand.execute();
  }
}
