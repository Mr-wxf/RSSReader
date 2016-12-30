package com.rssreader.wxf.rssreader.rssreader.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rssreader.wxf.rssreader.R;

public class MySubscribeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ib_back_subscribe;
    private TextView tv_manage;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscribe);
        this.mContext = this;
        initUI();
    }

    private void initUI() {
        ib_back_subscribe = (ImageButton) findViewById(R.id.ib_back_subscribe);
        tv_manage = (TextView) findViewById(R.id.tv_manage);
        ib_back_subscribe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back_subscribe:
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
                break;
        }
    }
}
