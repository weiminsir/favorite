package coder.wick.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hks on 2016/6/24.
 */
public abstract class BaseViewHolder<M> extends RecyclerView.ViewHolder {

    protected M mData;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) itemView.findViewById(id);
    }

    /**
     * 有次数据传递  可在hold里面处理数据
     */
    public void setData(M data) {
        mData = data;
    }

    protected Context getContext() {
        return itemView.getContext();
    }

}
