package com.rssreader.wxf.rssreader.rssreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rssreader.wxf.rssreader.R;
import com.rssreader.wxf.rssreader.rssreader.bean.News;
import com.rssreader.wxf.rssreader.rssreader.database.NewsDao;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/29.
 */
public class CollcetionListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<News> newsList;
    private final NewsDao newsDao;

    public CollcetionListViewAdapter(Context context, ArrayList<News> newsList) {
        this.mContext = context;
        this.newsList = newsList;
        newsDao = new NewsDao(mContext);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.collection_listview_item, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.bt_delete_collection = (Button) convertView.findViewById(R.id.bt_delete_collection);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String description = newsList.get(position).getDescription();
        String substring = "";
        if (description.contains("a")) {
            substring = description.trim().substring(description.indexOf("htm>") + 4, description.length() - 4);
        }
        holder.tv_title.setText(newsList.get(position).getTitle());
        holder.tv_content.setText(substring);
        holder.tv_date.setText(newsList.get(position).getDate());
        holder.bt_delete_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsDao.delCollection(newsList.get(position).getLink());
                newsList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        Button bt_delete_collection;
        TextView tv_date;
    }
}

