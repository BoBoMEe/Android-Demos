package com.bobomee.android.annotationdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bobomee.android.layout.Layout;
import com.bobomee.android.layout.LayoutBinder;

/**
 * Created on 2016/11/30.下午4:52.
 *
 * @author bobomee.
 */

@Layout(R.layout.layout_bind_activity) public class LayoutBinderActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    LayoutBinder.bind(this);
  }

  @Layout(R.layout.layout_fragment) public static class LayoutBinderFragment extends Fragment {

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
      return LayoutBinder.inflate(this);
    }
  }
}
