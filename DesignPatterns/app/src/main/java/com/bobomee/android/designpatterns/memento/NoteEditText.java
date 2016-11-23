package com.bobomee.android.designpatterns.memento;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created on 16/8/25.下午9:49.
 *
 * @author bobomee.
 * @description:
 */
public class NoteEditText extends EditText {
  public NoteEditText(Context context) {
    super(context);
  }

  public NoteEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NoteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public NoteEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  //创建备忘录对象,即存储编辑器的指定数据
  public Memoto createMemoto(){
    Memoto memoto = new Memoto();
    memoto.text   = getText().toString();
    memoto.cursor = getSelectionStart();
    return memoto;
  }

  public void restore(Memoto memoto){
    setText(memoto.text);
    setSelection(memoto.cursor);
  }
}
