package coder.wick.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weimin on 4/9/2016.
 */
public class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> mDataList;
    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected Context mActivity;
    protected AbsListView mListView;

    public BaseListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDataList = new ArrayList<>();
    }

    public BaseListAdapter(Activity activity) {
        this.mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        mDataList = new ArrayList<>();
    }

    public BaseListAdapter(Context context, AbsListView listView) {
        this(context);
        mListView = listView;
        mListView.setAdapter(this);
    }

    public void setDataList(List<T> dataList) {

        if (dataList != null) {
            mDataList = dataList;
        }
        notifyDataSetChanged();
    }

    public void appendDataList(List<T> dataList) {
        if (mDataList != null) {
            mDataList.clear();
        }
        if (null == mDataList) {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }


    public List<T> getDataList() {
        return mDataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        return convertView;
    }
}
