package com.rssreader.wxf.rssreader.rssreader.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public class XMLUtil {
    private Context mContext;
    private ArrayList<Object> list;
    private NewsListListner newsListListner;

    public XMLUtil(Context context) {
        this.mContext = context;
    }

    public void ReadXml(String url, final Class<?> clazz,
                        final List<String> fields, final List<String> elements, final String itemElement) {
        list = new ArrayList<>();
        RequestQueue mQueue = Volley.newRequestQueue(mContext);

        XMLRequest xmlRequest = new XMLRequest(url,
                new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser response) {
                        try {

                            int eventType = response.getEventType();
                            Object obj = null;
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        if (itemElement.equals(response.getName())) {
                                            obj = clazz.newInstance();
                                        }
                                        if (obj != null
                                                && elements.contains(response.getName())) {
                                            setFieldValue(obj, fields.get(elements
                                                            .indexOf(response.getName())),
                                                    response.nextText());

                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        response.getName();
                                        if (itemElement.equals(response.getName())) {
                                            list.add(obj);
                                            obj = null;

                                        }
                                        break;
                                }
                                eventType = response.next();
                            }
                            newsListListner.getList(list);
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        mQueue.add(xmlRequest);
    }


    class XMLRequest extends Request<XmlPullParser> {

        private final Response.Listener<XmlPullParser> mListener;

        public XMLRequest(int method, String url, Response.Listener<XmlPullParser> listener,
                          Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            mListener = listener;
        }

        public XMLRequest(String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errorListener) {
            this(Method.GET, url, listener, errorListener);
        }

        @Override
        protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
            try {
                String xmlString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = factory.newPullParser();
                xmlPullParser.setInput(new StringReader(xmlString));
                return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (XmlPullParserException e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(XmlPullParser response) {
            mListener.onResponse(response);
        }

    }

    public void setFieldValue(Object obj, String propertyName,
                              Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public void setNewsList(NewsListListner newsList) {
        this.newsListListner = newsList;
    }

    public interface NewsListListner {
        void getList(ArrayList<Object> objects);
    }
}

