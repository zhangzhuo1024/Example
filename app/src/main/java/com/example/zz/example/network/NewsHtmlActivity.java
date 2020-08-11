package com.example.zz.example.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.example.zz.example.R;

public class NewsHtmlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_html);
        String url = getIntent().getStringExtra("url");
        WebView webView = findViewById(R.id.webView);
        webView.loadUrl(url);
    }
}