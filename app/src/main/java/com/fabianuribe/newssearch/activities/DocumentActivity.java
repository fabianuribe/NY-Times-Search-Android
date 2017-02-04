package com.fabianuribe.newssearch.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fabianuribe.newssearch.R;
import com.fabianuribe.newssearch.models.Doc;

/**
 * Created by uribe on 2/3/17.
 */

public class DocumentActivity extends AppCompatActivity {

    private Doc document;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_detail);


        document = getIntent().getParcelableExtra("document");
        String url = document.getWebUrl();

        WebView webView = (WebView) findViewById(R.id.wvDocumentDetail);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                 view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
}
