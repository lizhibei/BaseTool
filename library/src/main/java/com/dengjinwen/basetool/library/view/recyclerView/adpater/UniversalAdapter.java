package com.dengjinwen.basetool.library.view.recyclerView.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class UniversalAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater inflater;
    protected ViewGroup mRv;
    private OnItemClickListener mOnItemClickListener;

    public UniversalAdapter(Context mContext, int mLayoutId, List<T> mDatas) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        this.inflater=LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder viewHolder=ViewHolder.get(this.mContext,null, viewGroup,mLayoutId);
        if(null==this.mRv){
            this.mRv=viewGroup;
        }
        return viewHolder;
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected int getPosition(android.support.v7.widget.RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        this.setListener(i, (ViewHolder) viewHolder);
        this.convert((ViewHolder) viewHolder, this.mDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return this.mDatas != null?this.mDatas.size():0;
    }

    public void setDatas(List<T> list) {
        if(this.mDatas != null) {
            if(null != list) {
                ArrayList temp = new ArrayList();
                temp.addAll(list);
                this.mDatas.clear();
                this.mDatas.addAll(temp);
            } else {
                this.mDatas.clear();
            }
        } else {
            this.mDatas = list;
        }

        this.notifyDataSetChanged();
    }

    public void remove(int i) {
        if(null != this.mDatas && this.mDatas.size() > i && i > -1) {
            this.mDatas.remove(i);
            this.notifyDataSetChanged();
        }

    }

    public void addDatas(List<T> list) {
        if(null != list) {
            ArrayList temp = new ArrayList();
            temp.addAll(list);
            if(this.mDatas != null) {
                this.mDatas.addAll(temp);
            } else {
                this.mDatas = temp;
            }

            this.notifyDataSetChanged();
        }

    }

    public List<T> getDatas() {
        return this.mDatas;
    }

    public T getItem(int position) {
        return position > -1 && null != this.mDatas && this.mDatas.size() >
                position?this.mDatas.get(position):null;
    }

    /** @deprecated */
    @Deprecated
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if(this.isEnabled(viewType)) {
            viewHolder.itemView.setOnClickListener(v -> {
                if(UniversalAdapter.this.mOnItemClickListener != null) {
                    int position = UniversalAdapter.this.getPosition(viewHolder);
                    UniversalAdapter.this.mOnItemClickListener.onItemClick(parent,
                            v, UniversalAdapter.this.mDatas.get(position), position);
                }

            });
            viewHolder.itemView.setOnLongClickListener(v -> {
                if(UniversalAdapter.this.mOnItemClickListener != null) {
                    int position = UniversalAdapter.this.getPosition(viewHolder);
                    return UniversalAdapter.this.mOnItemClickListener.onItemLongClick(parent,
                            v, UniversalAdapter.this.mDatas.get(position), position);
                } else {
                    return false;
                }
            });
        }
    }

    protected void setListener(final int position, final ViewHolder viewHolder) {
        if(this.isEnabled(this.getItemViewType(position))) {
            viewHolder.itemView.setOnClickListener(v -> {
                if(UniversalAdapter.this.mOnItemClickListener != null) {
                    UniversalAdapter.this.mOnItemClickListener.onItemClick(UniversalAdapter.this.mRv, v, UniversalAdapter.this.mDatas.get(position), position);
                }

            });
            viewHolder.itemView.setOnLongClickListener(v -> {
                if(UniversalAdapter.this.mOnItemClickListener != null) {
                    int position1 = UniversalAdapter.this.getPosition(viewHolder);
                    return UniversalAdapter.this.mOnItemClickListener.onItemLongClick(UniversalAdapter.this.mRv, v, UniversalAdapter.this.mDatas.get(position1), position1);
                } else {
                    return false;
                }
            });
        }
    }

    // 将 var2的数据 显示在ViewHolder 的对应 View 中
    public abstract void convert(ViewHolder var1, T var2);

    public UniversalAdapter setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener=onItemClickListener;
        return this;
    }

    public OnItemClickListener getmOnItemClickListener(){
        return mOnItemClickListener;
    }
}
