package com.handmark.pulltorefresh.library.internal;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqi on 2015/6/24.
 */
public class HistogramProgressBar extends LinearLayout{
    private int colorRes;
    private String text;
    private int textColorRes;
    private List<FrameLayout> blocklist = new ArrayList<>();
    private List<ObjectAnimator> blockAnim = new ArrayList<>();
    private int index;
    private LinearLayout linearLayout;

    public HistogramProgressBar(Context context) {
        super(context);
        init();
    }

    public HistogramProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistogramProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(Utils.dp2px(getContext(), 4), Utils.dp2px(getContext(), 12));
        layoutParams.setMargins(Utils.dp2px(getContext(), 2), 0, Utils.dp2px(getContext(), 2), Utils.dp2px(getContext(), 8));

        for (int i = 0;i < 6; i ++){
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(layoutParams);
            frameLayout.setBackgroundColor(getResources().getColor(R.color.common_element_blur_w));
            blocklist.add(frameLayout);
            linearLayout.addView(frameLayout);
        }
//        TextView textView = new TextView(getContext());
//        textView.setText("加载中");
        this.addView(linearLayout);
//        this.addView(textView);
        for (FrameLayout block: blocklist){
            ObjectAnimator rotationXAnim = ObjectAnimator.ofFloat(block, "rotationX", 0.0F, 360.0F);
            rotationXAnim.setDuration(2000);
            rotationXAnim.setRepeatCount(ObjectAnimator.INFINITE);
            rotationXAnim.setRepeatMode(ObjectAnimator.RESTART);
            blockAnim.add(rotationXAnim);
        }

        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index == 6) return;
                blockAnim.get(index).start();
                index ++ ;
                HistogramProgressBar.this.postDelayed(this, 100);
            }
        }, 100);
    }

    public void setText(String text){
        this.text = text;
    }

    public void setTextColor(int textColorRes){
        this.textColorRes = textColorRes;
    }

    public void setColor(int colorRes){
        this.colorRes = colorRes;
    }
}
