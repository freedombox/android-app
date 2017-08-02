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

package org.freedombox.freedombox.NetworkModule

import android.app.Activity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.freedombox.freedombox.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

fun getFBXApps(url: String, activity: Activity, freedombox_url: String): JSONArray {

    var services: JSONArray = JSONArray()

    fun getServicesFromFile(): JSONArray {
        val stream = activity.assets.open(url)
        val services = JSONObject(stream.bufferedReader().use { it.readText() })
        return services.getJSONArray("services")
    }

    if (BuildConfig.DEBUG) {
        return getServicesFromFile()
    } else {
        val requestQueue = Volley.newRequestQueue(activity)
        val url = listOf<String>(url, freedombox_url).joinToString(separator = "/")
        val jsonObjectResponse = JsonObjectRequest(Request.Method.GET, url, null,
                Listener<JSONObject> { response -> services = JSONArray(response!!.get("services")) },
                Response.ErrorListener { services = getServicesFromFile() })
        requestQueue.add(jsonObjectResponse)
    }
    return services
}

