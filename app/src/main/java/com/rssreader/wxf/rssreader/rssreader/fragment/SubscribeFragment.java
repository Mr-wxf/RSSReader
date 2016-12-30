package com.rssreader.wxf.rssreader.rssreader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rssreader.wxf.rssreader.R;
import com.rssreader.wxf.rssreader.rssreader.activity.MySubscribeActivity;


public class SubscribeFragment extends Fragment implements View.OnClickListener {

    private ImageButton ib_item_move;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribe, null);
        ib_item_move = (ImageButton) view.findViewById(R.id.ib_item_move);
        ib_item_move.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_item_move:
                Intent intent = new Intent(getContext(), MySubscribeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
