/*
 * This file is part of FreedomBox.
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

package org.freedombox.freedombox.utils.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

fun getFBXApps(context: Context, uri: String, onSuccess: (JSONObject) -> Unit,
               onFailure: () -> Unit) {
    val requestQueue = Volley.newRequestQueue(context)

    val jsonObjectResponse = JsonObjectRequest(
        Request.Method.GET,
        uri,
        null,
        Response.Listener<JSONObject> {
            onSuccess(it)
        },
        Response.ErrorListener {
            onFailure()
        }
    )
    requestQueue.add(jsonObjectResponse)
}
