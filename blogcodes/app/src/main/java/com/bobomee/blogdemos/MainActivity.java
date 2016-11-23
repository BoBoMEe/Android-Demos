package com.bobomee.blogdemos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.bobomee.blogdemos.activityfragment.ActivityFragment;
import com.bobomee.blogdemos.base.BaseListActivity;
import com.bobomee.blogdemos.dialog_fragment.BottomViewActivity;
import com.bobomee.blogdemos.glide.GlidePlayActivity;
import com.bobomee.blogdemos.js_native.JsJavaInteractive;
import com.bobomee.blogdemos.recycler.main.RecyclerViewActivity;
import com.bobomee.blogdemos.scroll.View_st_sb;
import com.bobomee.blogdemos.tab_head_list_vp.ClipViewPagerListActivity;
import com.bobomee.blogdemos.tools.ScreenShotActivity;
import com.bobomee.blogdemos.view.arcmenu.ArcMenuActivity;
import com.bobomee.blogdemos.view.compound.CompoundDrawablesTextViewActivity;
import com.bobomee.blogdemos.view.index.IndexListActivity;
import com.bobomee.blogdemos.view.switcher3d.Image3DSwitchActivity;

public class MainActivity extends BaseListActivity {

    private String[] ITEMS = {"ArcMenuActivity",
            "IndexListActivity",
            "ScreenShotActivity",
            "JsJavaInteractive",
            "BottomViewActivity",
            "CompoundDrawablesTextViewActivity",
            "Image3DSwitchActivity", "ClipViewPagerListActivity", "GlidePlayActivity",
        "RecyclerViewActivity","ViewScrollTo_ScrollBy","ActivityFragment"
    };
    private Class[] CLS = {ArcMenuActivity.class,
            IndexListActivity.class,
            ScreenShotActivity.class,
            JsJavaInteractive.class,
            BottomViewActivity.class,
            CompoundDrawablesTextViewActivity.class,
            Image3DSwitchActivity.class, ClipViewPagerListActivity.class, GlidePlayActivity.class,
        RecyclerViewActivity.class,
        View_st_sb.class, ActivityFragment.class
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setListAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return ITEMS.length;
            }

            @Override
            public Object getItem(int position) {
                return ITEMS[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (null == convertView) {
                    convertView = View.inflate(MainActivity.this, R.layout.activity_main_list_item, null);
                    holder = new ViewHolder();
                    holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.text1.setText((CharSequence) getItem(position));

                return convertView;
            }
        });
    }


    static class ViewHolder {
        TextView text1;
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(this,CLS[position]);

        startActivity(intent);

    }
}
