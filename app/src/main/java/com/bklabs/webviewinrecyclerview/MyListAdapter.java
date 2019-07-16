package com.bklabs.webviewinrecyclerview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyHolder> {
    List<ItemData> listData;
    private int screenWith;
    private String style =
            "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=no\" />"+
            "<script type=\"text/javascript\">" +
                    "window.onload = function () {" +
                    "var content = document.getElementById(\"contentMessage\");" +
                    "AndroidInterface.getWidthContent(content.offsetWidth);" +
                    "}" +
                    "</script>" +

                    "<style>" +
                    "body {" +
                    "margin: 0 !important;" +
                    "padding: 0 !important;" +
                    "word-wrap:break-word;" +
                    "}" +

                    "table {" +
                    "  border-collapse: collapse;" +
                    "}" +

                    "#bodyContent {" +
                    "display: flex;" +
                    "background-color:transparent;" +
                    "}" +

                    "#contentMessage{" +
                    "background-color:transparent;" +
                    "word-wrap:break-word;" +
                    "}" +

                    "</style>";

    //<head> <meta name="viewport" content="width=device-width, user-scalable=no" />
    public MyListAdapter(List<ItemData> listData, int width) {
        this.listData = listData;
        this.screenWith = width;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_rv, viewGroup, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder,final  int i) {
        ItemData itemData = listData.get(i);
        String contentWeb = "<body><div id=\"bodyContent\" style=\"display: flex;\"><div id=\"contentMessage\">"
                + itemData.getContent()
                + "</div></div></body>" + style;
        myHolder.name.setText(itemData.getName());
        myHolder.webView.removeJavascriptInterface("AndroidInterface");
        myHolder.webView.loadData(contentWeb, "text/html", "UTF-8");
        myHolder.webView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void getWidthContent(final String withContent) {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Math.min(screenWith / 2, convertDpSize( myHolder.webView.getContext(), Integer.valueOf(withContent) + 10)), ViewGroup.LayoutParams.WRAP_CONTENT);
                        myHolder.webView.setLayoutParams(params);
                    }
                });
            }
        },"AndroidInterface");

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private WebView webView;
        private TextView name;
        private LinearLayout layoutContent;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.web);
            name = itemView.findViewById(R.id.name);
            layoutContent = itemView.findViewById(R.id.layout_content);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
//            webView.addJavascriptInterface(new WebAppInterface(webView.getContext(), webView), "AndroidInterface");
        }
    }

    class WebAppInterface {
        Context mContext;
        WebView webView;

        WebAppInterface(Context c, WebView web) {
            mContext = c;
            webView = web;
        }

        @JavascriptInterface
        public void getWidthContent(final String withContent) {

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("tranhoasss",screenWith+" "+ withContent);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Math.min(screenWith / 2, convertDpSize(mContext, Integer.valueOf(withContent) + 10)), ViewGroup.LayoutParams.WRAP_CONTENT);
                    webView.setLayoutParams(params);
                }
            });
        }
    }

    private int convertDpSize(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) ((float) px * density + 0.5f);
    }
}
