package coder.wick.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import coder.mylibrary.R;

public abstract class BaseFragment extends RxFragment {

    protected final static int DEFAULT_MENU_RES = -1;
    protected View mRootView;
    protected Context mContext;
    protected BaseActivity mActivity;
    protected TextView mTitle;
    protected Toolbar mToolbar;

    abstract protected int getLayoutResID();

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
        mContext = context;
    }

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(getLayoutResID(), container, false);
        ButterKnife.bind(this, mRootView);

        if (getMenuLayoutResID() != DEFAULT_MENU_RES) {
            setHasOptionsMenu(true);
        }
        return mRootView;
    }

    //    protected void setTitle(CharSequence title) {
//        mTitle = (TextView) mActivity.findViewById(R.id.toolbar_title);
//        if (mActivity != null && mActivity.getSupportActionBar() != null) {
//            mTitle.setText(title);
//        }
//    }
    protected void setupToolbar() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            mActivity.setSupportActionBar(mToolbar);
        }
    }

    public void setTitle(String title) {
        mTitle = (TextView) mRootView.findViewById(R.id.title);
        if (mToolbar != null) {
            mTitle.setText(title);
        }
    }

    protected int getMenuLayoutResID() {
        return DEFAULT_MENU_RES;
    }

    @Override
    @CallSuper
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getMenuLayoutResID() != DEFAULT_MENU_RES) {
            menu.clear();
            inflater.inflate(getMenuLayoutResID(), menu);
        } else {
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment);
        }
    }

    //移除fragment
    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }
}
