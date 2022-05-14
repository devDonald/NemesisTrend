package com.nemesis_trend.app;

import static android.app.PendingIntent.getActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private String current_url = "https://thenemesistrend.com/";
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);



        loadWeb(current_url);
        MobileAds.initialize(this, initializationStatus -> {
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



    }
    @Override
    public void onBackPressed()
    {
        WebView webView = (WebView) findViewById(R.id.webView);
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    private static class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //hud.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            if(hud!=null){
//                hud.dismiss();
//            }
        }
    }
    public void loadWeb(String url){

        webView.setWebViewClient(new MyWebViewClient());


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(url);

        //webView.loadUrl("https://reportcovid19.org/riskareas");

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);


        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                current_url =url;
                return super.shouldOverrideUrlLoading(view, url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/error.html");
            }

            public  void  onPageFinished(WebView view, String url){
                //Hide the SwipeReefreshLayout
            }

        });
    }

    @Override
    public void onResume(){
        super.onResume();

        //((MainActivity) getActivity()).setActionBarTitle("High Risk Areas");

    }
}