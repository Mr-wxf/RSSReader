package com.rssreader.wxf.rssreader.rssreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.rssreader.wxf.rssreader.R;


public class FindFragment extends Fragment implements View.OnClickListener {

    private ListView lv_find_news;
    private EditText et_find_item;
    private ImageButton ib_clear_item;
    private ImageButton ib_find_item;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);
        lv_find_news = (ListView) view.findViewById(R.id.lv_find_news);
        et_find_item = (EditText) view.findViewById(R.id.et_find_item);
        ib_clear_item = (ImageButton) view.findViewById(R.id.ib_clear_item);
        ib_find_item = (ImageButton) view.findViewById(R.id.ib_find_item);
        et_find_item.setOnClickListener(this);
        ib_clear_item.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_find_item:

                break;
            case R.id.ib_clear_item:
                et_find_item.setText("");
                break;
        }
    }
}
