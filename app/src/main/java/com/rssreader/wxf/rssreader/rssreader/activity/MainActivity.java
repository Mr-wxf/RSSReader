package com.rssreader.wxf.rssreader.rssreader.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.rssreader.wxf.rssreader.R;
import com.rssreader.wxf.rssreader.rssreader.fragment.CollectionFragment;
import com.rssreader.wxf.rssreader.rssreader.fragment.FindFragment;
import com.rssreader.wxf.rssreader.rssreader.fragment.SubscribeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private RadioButton rb_subscribe;
    private ArrayList<Fragment> fragmentList;
    private FrameLayout fl;
    private RadioButton rb_collection;
    private RadioButton rb_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = this;
        initUI();
        rb_subscribe.setOnClickListener(this);
        rb_collection.setOnClickListener(this);
        rb_find.setOnClickListener(this);
    }



    private void initUI() {
        rb_subscribe = (RadioButton) findViewById(R.id.rb_subscribe);
        fl = (FrameLayout) findViewById(R.id.fl);
        rb_collection = (RadioButton) findViewById(R.id.rb_collection);
        rb_find = (RadioButton) findViewById(R.id.rb_find);
        FragmentTransaction subscribeTransaction = getSupportFragmentManager().beginTransaction();
        subscribeTransaction.replace(R.id.fl,new SubscribeFragment());
        subscribeTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_subscribe:
                FragmentTransaction subscribeTransaction = getSupportFragmentManager().beginTransaction();
                subscribeTransaction.replace(R.id.fl,new SubscribeFragment());
                subscribeTransaction.commit();
                break;
            case R.id.rb_collection:
                FragmentTransaction collectionTransaction = getSupportFragmentManager().beginTransaction();
                collectionTransaction.replace(R.id.fl,new CollectionFragment());
                collectionTransaction.commit();
                break;
            case R.id.rb_find:
                FragmentTransaction findTransaction = getSupportFragmentManager().beginTransaction();
                findTransaction.replace(R.id.fl,new FindFragment());
                findTransaction.commit();
                break;
        }
    }
}