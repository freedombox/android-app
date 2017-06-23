package org.freedombox.freedombox

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView

class MainActivity : AppCompatActivity() {
    private var mWebView: WebView? = null

    override fun onCreate(paramBundle: Bundle?) {
        super.onCreate(paramBundle)
        setContentView(R.layout.activity_main)
        this.mWebView = findViewById(R.id.activity_main_webview) as WebView
        this.mWebView!!.addJavascriptInterface(WebAppInterface(this), "Android")
        this.mWebView!!.settings.javaScriptEnabled = true
        this.mWebView!!.loadUrl("file:///android_asset/index.html")
    }
}
