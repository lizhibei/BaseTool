package com.dengjinwen.basetool.library.function.selectImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dengjinwen.basetool.library.R;

import java.util.List;

public class SelectFileAdapter extends BaseAdapter {
    private Context mContext;
    private List<ImageFloder> data;
    private LayoutInflater inflater;

    public SelectFileAdapter(Context mContext, List<ImageFloder> data) {
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
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.select_file_item,null);
            holder.image_iv=convertView.findViewById(R.id.image_iv);
            holder.name_tv=convertView.findViewById(R.id.name_tv);
            holder.number_tv=convertView.findViewById(R.id.number_tv);
            holder.select_iv=convertView.findViewById(R.id.select_iv);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        ImageFloder imageFloder=data.get(position);
        Glide.with(mContext)
                .load(imageFloder.getFirstImagePath())
                .error(R.drawable.image)
                .into(holder.image_iv);
        holder.name_tv.setText(imageFloder.getName());
        holder.number_tv.setText(imageFloder.getCount()+mContext.getResources().getString(R.string.image_number_));
        if(imageFloder.isSelect()){
            holder.select_iv.setImageResource(R.drawable.icon_select);
        }else {
            holder.select_iv.setImageResource(R.drawable.icon_unselect);
        }
        return convertView;
    }

    private class ViewHolder{
        ImageView image_iv,select_iv;
        TextView name_tv,number_tv;
    }
}