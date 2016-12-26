package com.rssreader.wxf.rssreader.rssreader.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rssreader.wxf.rssreader.R;
import com.rssreader.wxf.rssreader.rssreader.Utils.XMLUtil;
import com.rssreader.wxf.rssreader.rssreader.adapter.ListViewAdapter;
import com.rssreader.wxf.rssreader.rssreader.bean.News;
import com.rssreader.wxf.rssreader.rssreader.view.RefreshListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class FindFragment extends Fragment implements View.OnClickListener, RefreshListView.OnRefreshListener {

    private RefreshListView lv_find_news;
    private EditText et_find_item;
    private ImageButton ib_clear_item;
    private ImageButton ib_find_item;
    private ArrayList<News> newsList;
    private static int start = 0;
    private ListViewAdapter listViewAdapter;
    private ArrayList<News> nowNewsList;
    private ProgressBar pb_loading;
    private XMLUtil xmlUtil;
    private Handler handler= new Handler(){
      @Override
      public void handleMessage(Message msg) {
          loadDataAndRefresh();
          pb_loading.setVisibility(View.GONE);
      }
  };
    private ImageButton ib_qRcode;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);
        lv_find_news = (RefreshListView) view.findViewById(R.id.lv_find_news);
        et_find_item = (EditText) view.findViewById(R.id.et_find_item);
        ib_clear_item = (ImageButton) view.findViewById(R.id.ib_clear_item);
        ib_find_item = (ImageButton) view.findViewById(R.id.ib_find_item);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        ib_qRcode = (ImageButton) view.findViewById(R.id.ib_QRcode);
        ib_find_item.setOnClickListener(this);
        ib_clear_item.setOnClickListener(this);
        et_find_item.setOnClickListener(this);
        lv_find_news.setRefreshListener(this);
        ib_qRcode.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_find_item:
                nowNewsList = new ArrayList<>();
                newsList = new ArrayList<>();
                xmlUtil = new XMLUtil(getContext());
                if (et_find_item.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "请输入RSS", Toast.LENGTH_SHORT).show();
                    break;
                }
                loadData();
                pb_loading.setVisibility(View.VISIBLE);
                break;
            case R.id.ib_clear_item:
                et_find_item.setText("");
                break;
            case R.id.ib_QRcode:

                break;
        }

    }

    private void loadData() {
        final List<String> fields = new ArrayList<String>();
        fields.add("title");
        fields.add("link");
        fields.add("description");
        final List<String> elements = new ArrayList<String>();
        elements.add("title");
        elements.add("link");
        elements.add("description");
        new Thread() {
            @Override
            public void run() {
               InputStream in = xmlUtil.send(et_find_item.getText().toString().trim());
                ArrayList<Object> objects = xmlUtil.getData(in, "UTF-8", News.class, fields, elements, "item");
                for (int i = 0; i < objects.size(); i++) {
                    News news = (News) objects.get(i);
                    newsList.add(news);
                }
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void loadDataAndRefresh() {
        start = 0;
        if (newsList != null&&newsList.size()>0) {
            nowNewsList.clear();
            for (int i = 0; i < 10; i++) {
                if (start >= newsList.size()-1) {
                    start = 0;
                }
                nowNewsList.add(newsList.get(start));
                start++;
            }
        }
        if (nowNewsList!=null) {
            listViewAdapter = new ListViewAdapter(nowNewsList, getContext());
            lv_find_news.setAdapter(listViewAdapter);
            lv_find_news.setOnRefresh();
        }
    }


    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                loadDataAndRefresh();
                if (listViewAdapter != null) {
                    listViewAdapter.notifyDataSetChanged();
                    lv_find_news.setOnRefresh();
                }
            }
        }, 1500);
    }


    @Override
    public void LoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    if (start >= newsList.size() - 1) {
                        start = 0;
                    }
                    nowNewsList.add(newsList.get(start));
                    start++;
                }

                if (nowNewsList != null) {
                    listViewAdapter = new ListViewAdapter(nowNewsList, getContext());
                    lv_find_news.setOnRefresh();
                }
            }
        }, 1500);
    }


}
