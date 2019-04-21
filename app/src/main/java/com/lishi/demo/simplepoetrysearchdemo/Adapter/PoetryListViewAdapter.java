package com.lishi.demo.simplepoetrysearchdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lishi.demo.simplepoetrysearchdemo.Item.PoetryItem;
import com.lishi.demo.simplepoetrysearchdemo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoetryListViewAdapter extends BaseAdapter {
    private List<PoetryItem> itemList;
    private LayoutInflater mInflater;  //加载自定义布局
    private Animation animation;
    private Map<Integer, Boolean> isFrist;


    public PoetryListViewAdapter(Context context,List<PoetryItem> list){
        this.itemList = list;
        mInflater = LayoutInflater.from(context);

        isFrist = new HashMap<Integer, Boolean>();
        animation = AnimationUtils.loadAnimation(context,R.anim.from_bottom_to_top);
    }

    @Override
    public Object getItem(int position){
        return itemList.get(position);
    }
    @Override
    public int getCount(){
        return itemList.size();
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup viewGroup){
/*        View viewPoetry = this.mInflater.inflate(R.layout.poetry_item_layout, null);
        //获取item对象
        PoetryItem item = this.itemList.get(position);
        //获取自定义布局View对象
        TextView poetryView = viewPoetry.findViewById(R.id.poetryView);
        TextView poetView = viewPoetry.findViewById(R.id.poetView);
        TextView titleView = viewPoetry.findViewById(R.id.titleView);
        //添加数据
        poetryView.setText(item.getContent());
        poetView.setText(item.getPoet());
        titleView.setText(item.getTitle());

        if (isFrist.get(position) == null || isFrist.get(position)) {
            isFrist.put(position, false);
            viewPoetry.startAnimation(animation);
        }*/

        PoetryItem item = this.itemList.get(position);
        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.poetry_item_layout, null);
            holder.poetryView = (TextView) convertView.findViewById(R.id.poetryView);
            holder.poetView = (TextView) convertView.findViewById(R.id.poetView);
            holder.titleView = (TextView) convertView.findViewById(R.id.titleView);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        convertView.setTag(holder);
        holder.poetView.setText(item.getPoet());
        holder.poetryView.setText(item.getContent());
        holder.titleView.setText(item.getTitle());

        if(isFrist.get(position) == null|| isFrist.get(position)){
            isFrist.put(position,false);
            convertView.startAnimation(animation);
        }

        return convertView;
    }

    public void update(List<PoetryItem> list){
        this.itemList = list;
    }


    class ViewHolder {
        TextView poetryView;

        TextView poetView;

        TextView titleView;
    }
}
