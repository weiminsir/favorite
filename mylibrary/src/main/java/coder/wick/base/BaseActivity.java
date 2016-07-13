package coder.wick.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import coder.mylibrary.R;
import coder.wick.helper.ActivityHelper;


/**
 * Created by hks on 2016/6/14.
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    protected final static int DEFAULT_MENU_RES = -1;
    protected final static int DEFAULT_LAYOUT_RES = -1;
    protected Toolbar mToolbar;
    protected TextView mTitle;
    ActivityHelper mActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResID());
        mActivityHelper = new ActivityHelper(this);
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity();
    }

    protected int getMenuLayoutResID() {
        return DEFAULT_MENU_RES;
    }

    protected int getContentViewResID() {
        return DEFAULT_LAYOUT_RES;
    }

    protected int getFragmentContentId() {
        return DEFAULT_MENU_RES;
    }

    protected void initData() {
    }


    protected void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
        }
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    protected void setTitle(String title) {
        mTitle = (TextView) findViewById(R.id.title);
        if (mToolbar != null) {
            mTitle.setText(title);
        }
    }

    public void setHomeBack(boolean enable) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);//设置显示隐藏
        //若要更换toolbar 图标   需要重新设置toolbar   目前妥协如此
//        getSupportActionBar().setHomeButtonEnabled(enable); //设置返回键可用
    }

    protected void initView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuLayoutResID() == DEFAULT_MENU_RES) {
            return super.onCreateOptionsMenu(menu);
        } else {
            getMenuInflater().inflate(getMenuLayoutResID(), menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = count - 1; i >= 0; i--) {
            Fragment fragment = fm.findFragmentByTag(fm.getBackStackEntryAt(i).getName());
            if (fragment != null && fragment.onOptionsItemSelected(item)) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    /**
     * 跳转到指定的Activity
     *
     * @param data           Activity之间传递数据，Intent的Extra key为Constant.EXTRA_NAME.DATA
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull Bundle data, @NonNull Class<?> targetActivity) {
        final Intent intent = new Intent();
        intent.putExtra("wick" + targetActivity.getSimpleName(), data);
        intent.setClass(this, targetActivity);
        startActivity(intent);
    }

    protected void getLatestData() {
        //方便子类复制

    }
}
