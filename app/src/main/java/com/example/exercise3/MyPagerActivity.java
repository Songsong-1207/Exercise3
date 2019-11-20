package com.example.exercise3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.exercise3.adapter.MyPagerAdapter;
import com.example.exercise3.bean.Bean;
import java.util.ArrayList;
import java.util.List;

public class MyPagerActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 返回
     */
    private Button mFanhui;
    private ViewPager mVp;
    private TextView mPage;
    private TextView mCreatedAt;
    private int page;
    private MyPagerAdapter myPagerAdapter;
    private List<Bean.ResultsBean> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pager);
        initView();
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 0);
        results = (ArrayList<Bean.ResultsBean>) intent.getSerializableExtra("array");
        initPager();
    }

    private void initPager() {
        myPagerAdapter = new MyPagerAdapter(this, results);
        mVp.setAdapter(myPagerAdapter);
        mVp.setCurrentItem(page);

        mPage.setText("当前图片位置："+(page+1));
        mCreatedAt.setText(results.get(page).getCreatedAt());

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPage.setText("当前图片位置："+(i+1));
                mCreatedAt.setText(results.get(i).getCreatedAt());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initView() {
        mFanhui = (Button) findViewById(R.id.fanhui);
        mFanhui.setOnClickListener(this);
        mVp = (ViewPager) findViewById(R.id.vp);
        mPage = (TextView) findViewById(R.id.page);
        mCreatedAt = (TextView) findViewById(R.id.createdAt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
