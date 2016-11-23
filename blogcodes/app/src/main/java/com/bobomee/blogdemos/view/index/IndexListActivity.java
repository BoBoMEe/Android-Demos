package com.bobomee.blogdemos.view.index;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobomee.android.common.util.StringUtil;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


/**
 * @author：BoBoMEe Created at 2016/1/5.
 */
public class IndexListActivity extends BaseActivity {

    private List<UserModel> datas = new ArrayList<>();
    private IndexView indexView;
    private TextView float_view;
    private RecyclerView id_recyclerview;
    private ArrayList<String> index = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.index_list_layout);

        initView();

        initData();
    }

    private void initData() {
        for (int i = 0; i < 100; ++i) {
            String userName = StringUtil.getRandomString();
            String userDes = StringUtil.getRandomString();
            int icon = R.mipmap.ic_launcher;

            datas.add(new UserModel(userDes, icon, userName));
            index.add(Character.toString(userName.charAt(0)).toUpperCase(Locale.getDefault()));
        }

        Collections.sort(datas, new SortIgnoreCase());
        Collections.sort(index);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        id_recyclerview.setLayoutManager(manager);

        id_recyclerview.setAdapter(new MyAdapter());

        indexView.setOnTouchLetterChangeListenner(new IndexView.OnTouchLetterChangeListenner() {

            @Override
            public void onTouchLetterChange(boolean isTouched, String s) {

                float_view.setText(s);
                if (isTouched) {
                    float_view.setVisibility(View.VISIBLE);
                } else {
                    float_view.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            float_view.setVisibility(View.GONE);
                        }
                    }, 100);
                }
                int position = index.indexOf(s);
                id_recyclerview.scrollToPosition(position);
            }
        });

    }

    public class SortIgnoreCase implements Comparator<UserModel> {
        public int compare(UserModel u1, UserModel u2) {
            if (TextUtils.isEmpty(u1.name)) {
                if (TextUtils.isEmpty(u2.name))
                    return 0;
                return 1;
            }
            if (TextUtils.isEmpty(u2.name)) return -1;
            return u1.name.compareToIgnoreCase(u2.name);
        }
    }

    private void initView() {
        id_recyclerview = (RecyclerView) findViewById(R.id.id_recyclerview);

        float_view = (TextView) findViewById(R.id.float_view);

        indexView = (IndexView) findViewById(R.id.indexView);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(getBaseContext(), R.layout.index_list_item, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            UserModel userModel = datas.get(position);

            holder.icon.setImageResource(userModel.icon);
            holder.text1.setText(userModel.name);
            holder.text2.setText(userModel.des);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView text1;
            TextView text2;

            public ViewHolder(View itemView) {
                super(itemView);

                icon = (ImageView) itemView.findViewById(android.R.id.icon);
                text1 = (TextView) itemView.findViewById(android.R.id.text1);
                text2 = (TextView) itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
