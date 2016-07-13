package coder.wick.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hks on 2016/6/24.
 */
public abstract class BaseListRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mDataList;
    protected Context mContext;
    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;

    public BaseListRecycleAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = new ArrayList<>();
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public BaseListRecycleAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    public void setDataList(List<T> dataList) {
        if (dataList != null) {
            mDataList = dataList;
        }
        notifyDataSetChanged();
    }

    public abstract int getResLayout();


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getResLayout(), parent, false);
        final BaseViewHolder viewHolder = OnCreateViewHolder(parent, viewType, view);
        //itemView 的点击事件
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(viewHolder.getAdapterPosition()));
        }

        if (mItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(v -> mItemLongClickListener.onItemClick(viewHolder.getAdapterPosition()));
        }
        return viewHolder;
    }

    public abstract BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType, View view);

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.itemView.setId(position);
        holder.setData(mDataList.get(position));
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }
}
