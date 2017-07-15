/* This file is part of FreedomBox.
 *
 * FreedomBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FreedomBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FreedomBox. If not, see <http://www.gnu.org/licenses/>.
 */

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
