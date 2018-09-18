package com.pro.ahmed.hardtask002.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pro.ahmed.hardtask002.R;

public class WebViewFragment extends Fragment {

    private static WebView myWebView;
    private ProgressBar pb;

    public static WebView getMyWebView() {
        return myWebView;
    }

    public WebViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        myWebView = (WebView) view.findViewById(R.id.webView);
        pb = (ProgressBar) view.findViewById(R.id.progressBar);
        myWebView.setWebViewClient(new WebViewFragment.MyWebViewClient()); // to open inside App
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true); // to support JavaScript
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // to support ScrollBars
        myWebView.loadUrl("https://termsfeed.com/blog/sample-terms-andconditions-template/");
        return view;
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            pb.setVisibility(View.GONE); // hide pb
            view.setVisibility(View.VISIBLE); //show webView
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pb.setVisibility(View.VISIBLE); // show pb
            pb.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }


}
