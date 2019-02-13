package com.uob.durgaraj.quickcook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecepiesWebViewActivity extends AppCompatActivity {

        private WebView webView;
        int selectedItem;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_webview);

            selectedItem = getIntent().getExtras().getInt("index");
            webView = (WebView) findViewById(R.id.webView);
            webView.setWebViewClient(new MyBrowser());
            String url = TabbarActivity.favList.get(selectedItem).getWebUrl();
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.loadUrl(url);


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Click action
                    Toast.makeText(RecepiesWebViewActivity.this, "Recipe has been saved and added to favourites", Toast.LENGTH_SHORT).show();
                }
            });
        }
        private class MyBrowser extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }
    }

