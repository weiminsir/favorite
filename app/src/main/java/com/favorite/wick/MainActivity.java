package com.favorite.wick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hello_world)
    TextView mTextView;
    @BindView(R.id.animation1)
    Button button1;
    @BindView(R.id.animation2)
    Button button2;
    private int mHeight;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mHeight = button2.getMeasuredHeight();

        ViewGroup.LayoutParams layoutParams = button2.getLayoutParams();
        mHeight = layoutParams.height;
        Toast.makeText(MainActivity.this, mHeight + "", Toast.LENGTH_SHORT).show();


//        ImageRequest

    }

    @OnClick(R.id.animation1)
    public void onClickOne() {
        if (button2.getVisibility() == View.VISIBLE) {
            translateUtils.collapse(button2, mHeight);
        } else {
            translateUtils.expand(button2, mHeight);
        }
    }


    public void loadTextAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_txt);
        animation.setFillAfter(true);
        mTextView.startAnimation(animation);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.isLongPress()) {
            System.exit(0);
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Toast.makeText(this, "长按返回键退出", Toast.LENGTH_SHORT).show();
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 2) {
            //do something else
            System.exit(0);
        }
        return false;
    }
}
