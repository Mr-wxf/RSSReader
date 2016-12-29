package com.rssreader.wxf.rssreader.rssreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rssreader.wxf.rssreader.R;
import com.rssreader.wxf.rssreader.rssreader.activity.WebViewActivity;
import com.rssreader.wxf.rssreader.rssreader.bean.News;
import com.rssreader.wxf.rssreader.rssreader.database.NewsDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/22.
 */
public class FindListViewAdapter extends BaseAdapter {
    private ArrayList<News> newsList;
    private Context mContext;
    private final NewsDao newsDao;
    private ArrayList<News> newsCollectionList;
    private boolean[] state;
    public FindListViewAdapter(ArrayList<News> newsList, Context mContext) {
        this.newsList = newsList;
        this.mContext = mContext;
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
    public View getView(final int position, View convertView, ViewGroup parent) {;
        state= new  boolean[newsList.size()] ;
        for (int i=0;i<newsList.size()-1;i++){
            state[i]=false;
        }
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.find_listview_item, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.bt_collect = (Button) convertView.findViewById(R.id.bt_collect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(newsList.get(position).getTitle());
        String substring = "";
        String description = newsList.get(position).getDescription();
        if (description.contains("a")) {
            substring = description.trim().substring(description.indexOf("htm>") + 4, description.length() - 4);
        }
        holder.tv_content.setText(substring);
        holder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = newsList.get(position).getLink();
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            }
        });
        newsCollectionList = newsDao.queryAllCollection();
        for (int i = 0; i < newsCollectionList.size(); i++) {
            if(newsList.get(position).getTitle().equals(newsCollectionList.get(i).getTitle())){
                state[position]= true;
            }
            if (!state[position]&&newsList.get(position).getTitle().equals(newsCollectionList.get(i).getTitle())) {
                holder.bt_collect.setText("已收藏");
                holder.bt_collect.setBackgroundColor(0xffcccccc);
                state[position]=true;
            }else{
                holder.bt_collect.setText("收藏");
                holder.bt_collect.setBackgroundColor(0xffff0000);
            }
        }

        holder.bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("已收藏".equals(holder.bt_collect.getText())) {
                    return;
                }
                String title = newsList.get(position).getTitle();
                String description2 = newsList.get(position).getDescription();
                String link = newsList.get(position).getLink();
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = sDateFormat.format(new java.util.Date());
                newsDao.addCollection(title, description2, link,date);
                holder.bt_collect.setText("已收藏");
                state[position]=true;
                holder.bt_collect.setBackgroundColor(0xffcccccc);
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

