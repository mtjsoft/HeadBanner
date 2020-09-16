package com.example.banner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.banner.transformer.MyPageTransformer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int IMAGE_COUNT = 7;
    private ViewPager viewPager;

    private ArrayList<Integer> urls = new ArrayList<>(IMAGE_COUNT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        setListener();
    }

    private void setListener() {
        // 将事件交由viewPager来处理
        findViewById(R.id.linearlayoutroot).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }

    private void initData() {
        urls.add(R.drawable.img1);
        urls.add(R.drawable.img2);
        urls.add(R.drawable.img6);
        urls.add(R.drawable.img4);
        urls.add(R.drawable.img5);
        urls.add(R.drawable.img6);
        urls.add(R.drawable.img1);
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setOffscreenPageLimit(urls.size());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(false, new MyPageTransformer(getBaseContext(), 80));
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(3);
            }
        });
    }


    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return IMAGE_COUNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_view_card, null);
            ImageView card = view.findViewById(R.id.iv);
            card.setImageResource(urls.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
