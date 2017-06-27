package org.freedombox.freedombox

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import kotlin.collections.List
import kotlin.collections.HashMap

class WebAppInterface internal constructor(context: Context) {
    internal var context = context
    internal var packages = HashMap<String, List<String>>()

    init {
        // TODO Refactor to generic names
        this.packages.put("voip", listOf("im.vector.alpha"))
        this.packages.put("video", listOf("org.videolan.vlc")) // TODO
        this.packages.put("radio", listOf("com.sound.ampache", "com.antoniotari.reactiveampacheapp"))
        this.packages.put("library", listOf("com.nextcloud.client", "com.ocloud24.android")) // TODO
        this.packages.put("messaging", listOf("im.vector.alpha"))

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"))
        val resolveInfo = this.context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        this.packages.put("internet", listOf(resolveInfo.resolvePackageName))
    }

    @JavascriptInterface
    fun launchApp(appName: String) {
        val packageManager = this.context.packageManager
        val packageCandidates = this.packages[appName]
        try {
            val selectedPackage = packageCandidates!!.firstOrNull { candidate ->
                packageManager.getLaunchIntentForPackage(candidate) != null
            } ?: throw NameNotFoundException()
            val intent = packageManager.getLaunchIntentForPackage(selectedPackage)
            intent.addCategory("android.intent.category.LAUNCHER")
            this.context.startActivity(intent)
        } catch (ex: NameNotFoundException) {
            Toast.makeText(this.context, "App for ${appName.capitalize()} is not installed",
                    Toast.LENGTH_SHORT).show()
            Log.i("ERROR:", appName + " is not installed")
        }
    }
}
