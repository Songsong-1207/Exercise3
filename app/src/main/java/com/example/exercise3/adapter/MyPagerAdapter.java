package com.example.exercise3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.exercise3.R;
import com.example.exercise3.bean.Bean;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Bean.ResultsBean> results;

    public MyPagerAdapter(Context context, List<Bean.ResultsBean> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.main_pager_layout, null);
        ImageView img = view.findViewById(R.id.img);
        Glide.with(context).load(results.get(position).getUrl()).into(img);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
