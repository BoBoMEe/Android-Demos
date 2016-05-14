package com.bobomee.android.retrofit2demo;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by bobomee on 16/5/14.
 */
public class LogAdapter extends ArrayAdapter<String
        > {
    public LogAdapter(Context context, List<String> logs) {
        super(context, R.layout.item_log, R.id.item_log, logs);
    }
}
