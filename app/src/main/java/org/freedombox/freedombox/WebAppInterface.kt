package org.freedombox.freedombox

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.webkit.JavascriptInterface

import java.util.HashMap

class WebAppInterface internal constructor(internal var context: Context) {
    internal var packageName: HashMap<String, String>

    init {
        packageName = HashMap<String, String>()
        packageName.put("sip", "com.csipsimple")
        packageName.put("vlc", "org.videolan.vlc")
    }

    @JavascriptInterface
    fun launchApp(app: String) {
        val manager = context.packageManager

        try {
            val launchIntent = manager.getLaunchIntentForPackage(packageName[app]) ?: throw PackageManager.NameNotFoundException()
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            context.startActivity(launchIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.i("ERROR:", app + " is not installed")
        }

    }
}