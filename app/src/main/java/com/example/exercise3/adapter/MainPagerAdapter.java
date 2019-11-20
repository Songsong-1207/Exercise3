package com.example.exercise3.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.exercise3.MyPagerActivity;
import com.example.exercise3.R;
import com.example.exercise3.bean.Bean;

import java.util.ArrayList;

public class MainPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Bean.ResultsBean> results;

    public MainPagerAdapter(Context context, ArrayList<Bean.ResultsBean> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = View.inflate(context, R.layout.main_pager_layout, null);
        ImageView img = view.findViewById(R.id.img);
        Glide.with(context).load(results.get(position).getUrl()).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyPagerActivity.class);
                intent.putExtra("page",position);
                intent.putExtra("array",results);
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
