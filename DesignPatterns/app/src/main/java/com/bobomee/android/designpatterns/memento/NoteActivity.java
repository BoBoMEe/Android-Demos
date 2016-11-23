package com.bobomee.android.designpatterns.memento;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.R;
import com.bobomee.android.designpatterns.util.LogAdapter;
import java.util.ArrayList;

/**
 * Created on 16/8/25.下午9:59.
 *
 * @author bobomee.
 * @description:
 */
public class NoteActivity extends AppCompatActivity {
  @BindView(R.id.text) NoteEditText mText;
  @BindView(R.id.undo) Button mUndo;
  @BindView(R.id.save) Button mSave;
  @BindView(R.id.redo) Button mRedo;

  NoteCaretaker mNoteCaretaker = new NoteCaretaker();
  @BindView(R.id.list) ListView mList;
  private LogAdapter mLogAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.memento_note_layout);
    ButterKnife.bind(this);

    mLogAdapter = new LogAdapter(this,new ArrayList<String>());
    mList.setAdapter(mLogAdapter);
  }

  @OnClick({
      R.id.undo, R.id.save, R.id.redo
  }) public void btn_click(View view) {
    switch (view.getId()) {
      case R.id.undo:
        mText.restore(mNoteCaretaker.getPrevMemoto());
        Toast.makeText(NoteActivity.this, "撤销...", Toast.LENGTH_SHORT).show();
        break;
      case R.id.save:
        Memoto memoto = mText.createMemoto();
        mNoteCaretaker.saveMemoto(memoto);
        mLogAdapter.add(memoto.text);
        mText.setText("");
        break;
      case R.id.redo:
        mText.restore(mNoteCaretaker.getNextMemoto());
        Toast.makeText(NoteActivity.this, "重做...", Toast.LENGTH_SHORT).show();
        break;
      default:
        break;
    }
  }



}
