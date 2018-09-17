package com.dengjinwen.basetool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dengjinwen.basetool.R;
import com.dengjinwen.basetool.library.tool.ScreenUitl;

import java.util.ArrayList;

public class SelectImageAndAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> data;
    private LayoutInflater inflater;

    public SelectImageAndAdapter(Context mContext, ArrayList<String> data) {
        this.mContext = mContext;
        this.data = data;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.select_image_item,null);
            holder=new ViewHolder();
            holder.image_iv=convertView.findViewById(R.id.image_iv);
            holder.select_iv=convertView.findViewById(R.id.select_iv);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.image_iv.getLayoutParams().width= ScreenUitl.getScreenWidth(mContext)/3;
        holder.image_iv.getLayoutParams().height=ScreenUitl.getScreenWidth(mContext)/3;
        Glide.with(mContext).load(data.get(position)).into(holder.image_iv);
        holder.select_iv.setVisibility(View.GONE);

        return convertView;
    }

    private class ViewHolder{
        ImageView image_iv,select_iv;
    }
}
