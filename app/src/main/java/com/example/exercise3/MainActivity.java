package com.example.exercise3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.exercise3.adapter.MainPagerAdapter;
import com.example.exercise3.adapter.RecyAdapter;
import com.example.exercise3.bean.Bean;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private RecyclerView mRecy;
    private LinearLayout mLine;
    private NavigationView mNv;
    private DrawerLayout mDl;
    private ArrayList<Bean.ResultsBean> results;
    private RecyAdapter recyAdapter;
    private Toolbar mToo;
    private ViewPager mPager;
    private Bean bean;
    private MainPagerAdapter mainPagerAdapter;
    private TextView mTooTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToo();
        initRecy();
        initData();

    }

    private void initPager() {
        mainPagerAdapter = new MainPagerAdapter(this, results);
        mPager.setAdapter(mainPagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mRecy.smoothScrollToPosition(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initToo() {
        mTooTitle.setText("户外花朵识别系统");
        mToo.setTitle("");
        setSupportActionBar(mToo);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDl, mToo, R.string.app_name, R.string.app_name);
        mDl.addDrawerListener(toggle);
        toggle.syncState();

        mDl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                mLine.setX(mNv.getWidth() * v);
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    private void initData() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder()
                .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/79/1")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                bean = new Gson().fromJson(json, Bean.class);
                results = (ArrayList<Bean.ResultsBean>) bean.getResults();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initPager();
                        recyAdapter.setResults(bean.getResults());
                        recyAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initRecy() {
        LinearLayoutManager line = new LinearLayoutManager(this);
        line.setOrientation(LinearLayout.HORIZONTAL);
        mRecy.setLayoutManager(line);

        results = new ArrayList<>();
        recyAdapter = new RecyAdapter(this, results);
        mRecy.setAdapter(recyAdapter);

        recyAdapter.setOnClick(new RecyAdapter.OnClick() {
            @Override
            public void onClickItem(int position) {
//                Glide.with(MainActivity.this).load(recyAdapter.getResults().get(position).getUrl()).into(mImg);
                mPager.setCurrentItem(position);
            }
        });
    }

    private void initView() {
        mRecy = (RecyclerView) findViewById(R.id.recy);
        mLine = (LinearLayout) findViewById(R.id.line);
        mNv = (NavigationView) findViewById(R.id.nv);
        mDl = (DrawerLayout) findViewById(R.id.dl);
        mToo = (Toolbar) findViewById(R.id.too);
        mPager = (ViewPager) findViewById(R.id.pager);
        mTooTitle = (TextView) findViewById(R.id.tooTitle);
    }
}
