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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import org.freedombox.freedombox.BuildConfig
import org.freedombox.freedombox.Views.Fragments.LauncherFragment
import org.json.JSONObject

class AppLoader(val context: Context, val adapter: LauncherFragment.GridAdapter) {
    val parser = JsonParser()

    fun getServicesFromFile(path: String): JsonArray {
        val stream = context.assets.open(path)
        val jsonString = stream.bufferedReader().use {
            it.readText()
        }

        val jsonElement = parser.parse(jsonString)

        return jsonElement.asJsonObject["services"].asJsonArray
    }

    fun getFBXApps(url: String, freedomboxUrl: String) =
        if (BuildConfig.DEBUG) {
            adapter.setData(getServicesFromFile(url))
        }
        else {
            val requestQueue = Volley.newRequestQueue(context)
            val uri = listOf(freedomboxUrl, url).joinToString(separator = "/")
            val jsonObjectResponse = JsonObjectRequest(
                    Request.Method.GET,
                    uri,
                    null,
                    Response.Listener<JSONObject> { response ->
                        val services = parser.parse(response.get("services").toString())
                                .asJsonArray
                        adapter.setData(services)
                    },
                    Response.ErrorListener {
                        adapter.setData(getServicesFromFile(url))
                    }
            )
            requestQueue.add(jsonObjectResponse)
        }
}
