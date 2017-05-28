package org.freedombox.freedombox

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView

class MainActivity : AppCompatActivity() {

    private var mWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWebView = findViewById(R.id.activity_main_webview) as WebView
        mWebView!!.addJavascriptInterface(WebAppInterface(this), "Android")


        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true
        mWebView!!.loadUrl("file:///android_asset/index.html")
    }
}
