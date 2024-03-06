package com.kmj.sw_finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // WebView를 레이아웃에서 찾음
        WebView webView = findViewById(R.id.webView);

        // WebView 설정
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript를 사용하도록 설정

        // WebViewClient 설정 - 이걸 설정해야 새로운 창이 아닌 현재 WebView에서 열립니다.
        webView.setWebViewClient(new WebViewClient());

        // 웹페이지 띄우기
        webView.loadUrl("https://www.joeunday.co.kr/?page_id=5167");
    }
}