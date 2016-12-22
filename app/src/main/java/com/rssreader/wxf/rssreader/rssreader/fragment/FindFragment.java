package com.rssreader.wxf.rssreader.rssreader.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.test.LoaderTestCase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rssreader.wxf.rssreader.R;
import com.rssreader.wxf.rssreader.rssreader.Utils.XMLUtil;
import com.rssreader.wxf.rssreader.rssreader.adapter.ListViewAdapter;
import com.rssreader.wxf.rssreader.rssreader.bean.News;
import com.rssreader.wxf.rssreader.rssreader.view.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class FindFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener {

    private XListView lv_find_news;
    private EditText et_find_item;
    private ImageButton ib_clear_item;
    private ImageButton ib_find_item;
    private ArrayList<News> newsList;
    private static int start = 0;
    private Handler mHandler;
    private ListViewAdapter listViewAdapter;
    private ArrayList<News> nowNewsList;
    private ProgressBar pb_loading;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHandler = new Handler();
        View view = inflater.inflate(R.layout.fragment_find, null);
        lv_find_news = (XListView) view.findViewById(R.id.lv_find_news);
        et_find_item = (EditText) view.findViewById(R.id.et_find_item);
        ib_clear_item = (ImageButton) view.findViewById(R.id.ib_clear_item);
        ib_find_item = (ImageButton) view.findViewById(R.id.ib_find_item);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        ib_find_item.setOnClickListener(this);
        ib_clear_item.setOnClickListener(this);
        lv_find_news.setXListViewListener(this);
        et_find_item.setOnClickListener(this);
        lv_find_news.setPullLoadEnable(true);//是否开启上拉加载更多
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_find_item:
                if (et_find_item.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "请输入RSS", Toast.LENGTH_SHORT).show();
                    break;
                }
                nowNewsList = new ArrayList<>();
                loadData();
                pb_loading.setVisibility(View.VISIBLE);
                if(newsList!=null){
                    pb_loading.setVisibility(View.GONE);
                    refresh();
                    Log.d("TAG","我被电击了");
                }
                break;
            case R.id.ib_clear_item:
                et_find_item.setText("");
                break;
        }

    }

    private void loadData() {
        List<String> fields = new ArrayList<String>();
        fields.add("title");
        fields.add("link");
        fields.add("description");
        List<String> elements = new ArrayList<String>();
        elements.add("title");
        elements.add("link");
        elements.add("description");
        XMLUtil xmlUtil = new XMLUtil(getContext());

        xmlUtil.ReadXml(et_find_item.getText().toString().trim(), News.class, fields, elements, "item");
        xmlUtil.setNewsList(new XMLUtil.NewsListListner() {

            @Override
            public void getList(ArrayList<Object> objects) {
                newsList = new ArrayList<>();
                for (int i = 0; i < objects.size(); i++) {
                    newsList.add((News) objects.get(i));
                }
            }
        });

    }
    private void refresh() {
        start = 0;
        if (newsList != null) {
            nowNewsList.clear();
            for (int i = 0; i < 10; i++) {
                nowNewsList.add(newsList.get(start));
                if(start>=newsList.size()-1){
                    start=0;
                }
                start++;
            }
        }
        if (nowNewsList.size()!=0) {
            listViewAdapter = new ListViewAdapter(nowNewsList, getContext());
            lv_find_news.setAdapter(listViewAdapter);

        }
        onLoad();
    }

    private void onLoad() {
        lv_find_news.stopRefresh();
        lv_find_news.stopLoadMore();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        lv_find_news.setRefreshTime(date);
    }

    private void loadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    nowNewsList.add(newsList.get(start));
                    start++;
                    if(start>=newsList.size()-1){
                        start=0;
                    }
                }

                if (nowNewsList != null) {
                    listViewAdapter = new ListViewAdapter(nowNewsList, getContext());
                }
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                refresh();
                if(listViewAdapter!=null){
                    listViewAdapter.notifyDataSetChanged();
                }
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMore();
                listViewAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 1000);
    }
}
