package com.rssreader.wxf.rssreader.rssreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rssreader.wxf.rssreader.R;
import com.rssreader.wxf.rssreader.rssreader.bean.News;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/12/22.
 */
public class ListViewAdapter extends BaseAdapter {
    private ArrayList<News> newsList;
    private Context mContext;
    public ListViewAdapter(ArrayList<News> newsList,Context mContext){
        this.newsList=newsList;
        this.mContext=mContext;
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
            holder=new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText( newsList.get(position).title);
        holder.tv_content.setText( newsList.get(position).description);
        return convertView;
    }
  class ViewHolder{
      TextView tv_title;
      TextView tv_content;
  }
}

