package com.bobomee.android.designpatterns.chain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created on 16/8/18.下午11:46.
 *
 * @author bobomee.
 * @description:
 */
public class ChainActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    Button button = new Button(this);
    button.setText("Click");

    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, 150);

    addContentView(button, params);


    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        GroupLeader groupLeader = new GroupLeader();
        Director director = new Director();
        Manager manager = new Manager();
        Boss boss = new Boss();

        groupLeader.nextHandler = director;
        director.nextHandler = manager;
        manager.nextHandler = boss;

        groupLeader.handleRequest(50000);
      }
    });
  }
}
