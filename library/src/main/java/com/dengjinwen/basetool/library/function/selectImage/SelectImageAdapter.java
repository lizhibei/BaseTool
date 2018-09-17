package com.dengjinwen.basetool.library.function.selectImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.tool.ScreenUitl;
import com.dengjinwen.basetool.library.tool.log;

import java.io.File;
import java.util.HashSet;
import java.util.List;

public class SelectImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> data;
    private LayoutInflater inflater;
    private HashSet<String> selectImages;

    public SelectImageAdapter(Context mContext, List<String> data,HashSet<String> selectImages) {
        this.mContext = mContext;
        this.data = data;
        this.selectImages=selectImages;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        log.e("url:"+data.get(position));
        File file=new File(data.get(position));
        Glide.with(mContext).load(file).into(holder.image_iv);
        if(selectImages.contains(data.get(position))){
            holder.select_iv.setImageResource(R.drawable.icon_select);
        }else {
            holder.select_iv.setImageResource(R.drawable.icon_unselect);
        }

        int width= ScreenUitl.getScreenWidth(mContext)/3-20;
        holder.image_iv.getLayoutParams().width=width;
        holder.image_iv.getLayoutParams().height=width;

        final ImageView select=holder.select_iv;
        holder.select_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectImages.contains(data.get(position))){
                    select.setImageResource(R.drawable.icon_unselect);
                    selectImages.remove(data.get(position));
                }else {
                    select.setImageResource(R.drawable.icon_select);
                    selectImages.add(data.get(position));
                }
                listener.selectNumberChange();
//                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public  OnAdapterProcessListener listener;
    public void setOnAdapterProcessListener(OnAdapterProcessListener l){
        listener=l;
    }
    public interface OnAdapterProcessListener{
        public void selectNumberChange();
    }

    private class ViewHolder{
        ImageView image_iv,select_iv;
    }
}
