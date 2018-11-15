package com.daily.baselibrary.base;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.daily.baselibrary.R;


/**
 * 封装基类webView
 */
public class BaseWebActivity extends BaseActivity {

    public static final String TITLE = "title";
    public static final String URL = "url";

    private WebView webView;
    private LinearLayout rootView;
    private String mURL;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web;
    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rootView.removeView(webView);
        webView.destroy();
    }

    @Override
    public void initView() {
        webView = findView(R.id.base_web_view);
        rootView = findView(R.id.root_view);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            String title = getIntent().getStringExtra(TITLE);
            initToolbar(title, true);
            mURL = getIntent().getStringExtra(URL);
        }

        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setDefaultTextEncodingName("UTF-8");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100)
                    cancelLoading();
            }
        });

        webView.loadUrl(mURL);
        showLoading("加载中...");
    }
}
