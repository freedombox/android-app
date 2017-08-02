/** This file is part of FreedomBox.
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

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import org.freedombox.freedombox.BuildConfig
import org.json.JSONObject


fun getServicesFromFile(context: Context, path: String): JsonArray {
    val stream = context.assets.open(path)
    val jsonString = stream.bufferedReader().use {
        it.readText()
    }

    val parser = JsonParser()
    val jsonElement = parser.parse(jsonString)

    return jsonElement.asJsonObject["services"].asJsonArray
}

fun getFBXApps(url: String, context: Context, freedomboxUrl: String) =
        if (BuildConfig.DEBUG) {
            getServicesFromFile(context, url)
        } else {
            var services = JsonArray()
            val requestQueue = Volley.newRequestQueue(context)
            val uri = listOf(url, freedomboxUrl).joinToString(separator = "/")
            val jsonObjectResponse = JsonObjectRequest(
                    Request.Method.GET,
                    uri,
                    null,
                    Listener<JSONObject> { response ->
                        services = response.get("services") as JsonArray
                    },
                    Response.ErrorListener {
                        services = getServicesFromFile(context, uri)
                    }
            )
            requestQueue.add(jsonObjectResponse)

            services
        }
