package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.epoint.scan.ewm.R;

/**
 * @author lilin
 * @date 2016年9月28日 下午3:47:44
 * @annotation 打开URL
 */
public class OpenUrlActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openurlactivity);
		
		setTitle("加载网址");

		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		
		WebView webview = (WebView) findViewById(R.id.webview);
		
		webview.getSettings().setJavaScriptEnabled(true);
		// 不允许通过外部浏览器打开URL
		webview.setWebViewClient(new WebViewClient() {
	        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
	            view.loadUrl(url);
	            return true;
	        }
	    });

		webview.loadUrl(url);
		
		findViewById(R.id.closebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
