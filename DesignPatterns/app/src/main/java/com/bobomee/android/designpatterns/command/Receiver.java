package com.bobomee.android.designpatterns.command;

/**
 * Created on 16/8/19.下午10:36.
 *
 * @author bobomee.
 * @description:
 */
public class Receiver {
//真正执行具体命令逻辑的方法
  public void doAction(){
    System.out.print("执行具体的操作");
  }
}
