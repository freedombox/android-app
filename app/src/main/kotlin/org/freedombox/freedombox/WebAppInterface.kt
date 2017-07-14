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

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.util.Log
import android.webkit.JavascriptInterface
import kotlin.collections.List
import kotlin.collections.HashMap

class WebAppInterface internal constructor(context: Context) {
    internal var context = context
    internal var packages = HashMap<String, List<String>>()

    init {
        this.packages.put("sip", listOf("com.csipsimple"))
        this.packages.put("vlc", listOf("org.videolan.vlc"))
        this.packages.put("ampache", listOf("com.sound.ampache", "com.antoniotari.reactiveampacheapp"))
        this.packages.put("storage", listOf("com.nextcloud.client", "com.ocloud24.android"))

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"))
        val resolveInfo = this.context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        this.packages.put("browser", listOf(resolveInfo.resolvePackageName))
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
            Log.i("ERROR:", appName + " is not installed")
        }
    }
}
