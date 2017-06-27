package org.freedombox.freedombox

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import kotlin.collections.List
import kotlin.collections.HashMap

class WebAppInterface internal constructor(context: Context) {
    internal var context = context
    internal var intents = HashMap<String, List<Intent?>>()

    init {
        this.intents.put("voip", getLaunchIntent("im.vector.alpha"))
        this.intents.put("messaging", getLaunchIntent("im.vector.alpha"))
        this.intents.put("internet", getWebIntent("http://"))
        this.intents.put("library", getWebIntent("http://10.42.0.1:8080"))
        this.intents.put("video", getWebIntent("http://10.42.0.1:8096"))
        this.intents.put("radio", getLaunchIntent("com.sound.ampache",
                "com.antoniotari.reactiveampacheapp"))
    }

    fun getLaunchIntent(vararg packageNames: String): List<Intent> {
        return packageNames.map { packageName ->
            this.context.packageManager.getLaunchIntentForPackage(packageName)}
    }

    fun getWebIntent(vararg urls: String): List<Intent> {
        // TODO Launching browser works only if a default browser is set
        return urls.map { url ->  Intent(Intent.ACTION_VIEW, Uri.parse(url))}
    }

    @JavascriptInterface
    fun launchApp(appName: String) {
        val intent = this.intents[appName]!!.firstOrNull{ intent -> intent != null }
        if (intent != null) {
            this.context.startActivity(intent)
        } else {
            Toast.makeText(this.context, "App not installed",
                    Toast.LENGTH_SHORT).show()
            Log.i("ERROR:", "App for $appName is not installed")
        }
   }
}
