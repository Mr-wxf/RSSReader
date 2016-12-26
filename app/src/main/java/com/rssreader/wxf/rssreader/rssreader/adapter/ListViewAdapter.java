package com.rssreader.wxf.rssreader.rssreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.rssreader.wxf.rssreader.R;
import com.rssreader.wxf.rssreader.rssreader.activity.WebView;
import com.rssreader.wxf.rssreader.rssreader.bean.News;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/12/22.
 */
public class ListViewAdapter extends BaseAdapter {
    private ArrayList<News> newsList;
    private Context mContext;

    public ListViewAdapter(ArrayList<News> newsList, Context mContext) {
        this.newsList = newsList;
        this.mContext = mContext;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.bt_collect = (Button) convertView.findViewById(R.id.bt_collect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(newsList.get(position).title);
        String substring = "";
        String description = newsList.get(position).description;
        if (description.contains("a")) {
            substring = description.trim().substring(description.indexOf("htm>") + 4, description.length() - 4);
        }
        holder.tv_content.setText(substring);
        holder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = newsList.get(position).link;
                Intent intent = new Intent(mContext, WebView.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            }
        });
        holder.bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,position+"collect",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        Button bt_collect;

    }
}

