package com.example.banner.transformer;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.banner.utils.DimensionPixelUtil;

public class MyPageTransformer implements ViewPager.PageTransformer {

    // 最小缩放
    private static final float MIN_SCALE = 0.7f;

    // 每一个item需要移动的距离
    private float pagerTranslationX;

    // 需要覆盖的距离(就是ImageView压在另一个上面的距离 dp)
    private static final int dis = 15;

    /**
     * @param context    上下文
     * @param imageWigth 图片的原始宽度（dp，也就是xml中设置的宽度），下面计算时自己转PX
     */
    public MyPageTransformer(Context context, int imageWigth) {
        // 计算出两个view因为缩放所产生的间距 + 覆盖距离 = 需要移动的距离
        // 使用负号，便于计算左边的向右移，右边的向左移
        pagerTranslationX = -DimensionPixelUtil.dip2px(context, imageWigth * (1 - MIN_SCALE) + dis);
    }

    @Override
    public void transformPage(@NonNull View view, float position) {
        // 保证最小缩放 MIN_SCALE
        float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
        // 超出3个之后的缩放
        if (position < -3) { // 左边超出3个的，隐藏
            view.setScaleX(0);
            view.setScaleY(0);
            view.setTranslationX(pagerTranslationX * (position + 1));
        } else if (position < -1) { // < -1 时 缩放并移动 （ position +1 是因为左边第一个不移动，得去除它的移动距离）
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setTranslationX(pagerTranslationX * (position + 1));
        } else if (position < 0) {// [-1,0) 时 ，只缩放，不移动
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setTranslationX(0);
        } else if (position == 0) {  // 0 时 ，不缩放，不移动
            view.setScaleX(1);
            view.setScaleY(1);
            view.setTranslationX(0);
        } else if (position <= 1) { // (0,1] 时 ，只缩放，不移动
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setTranslationX(0);
        } else if (position <= 3) { // > 1  时 缩放并移动 （ position - 1 是因为右边第一个不移动，得去除它的移动距离）
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setTranslationX(pagerTranslationX * (position - 1));
        } else { // 右边超出3个的，隐藏
            view.setScaleX(0);
            view.setScaleY(0);
        }
    }
}
