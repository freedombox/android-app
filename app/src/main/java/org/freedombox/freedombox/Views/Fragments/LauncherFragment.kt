/**
 *  This file is part of FreedomBox.
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

package org.freedombox.freedombox.Views.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_launcher.appGrid
import org.freedombox.freedombox.APP_RESPONSE
import org.freedombox.freedombox.Components.AppComponent
import org.freedombox.freedombox.DEFAULT_IP
import org.freedombox.freedombox.NetworkModule.getFBXApps
import org.freedombox.freedombox.R
import org.freedombox.freedombox.SERVICES_URL
import org.freedombox.freedombox.Utils.ImageRenderer
import org.freedombox.freedombox.Views.Adapter.GridAdapter
import org.json.JSONObject
import javax.inject.Inject


class LauncherFragment : BaseFragment() {

    @Inject lateinit var imageRenderer: ImageRenderer
    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun getLayoutId() = R.layout.fragment_launcher

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = GridAdapter(activity.applicationContext, imageRenderer)
        appGrid.adapter = adapter

        val onSuccess = fun (response: JSONObject) {
            val services = JsonParser().parse(response["services"].toString())
                    .asJsonArray
            sharedPreferences.edit().putString(APP_RESPONSE, services.toString()).apply()
            adapter.setData(services)
        }

        val onFailure = fun() {
            if (sharedPreferences.contains(APP_RESPONSE)) {
                val services = sharedPreferences.getString(APP_RESPONSE, "[]")
                adapter.setData(JsonParser().parse(services).asJsonArray)
            }
            else
                showSnackMessage(getString(R.string.msg_apps_not_available))
        }

        //TODO: Use the URL from settings once it is setup
        val url = listOf(DEFAULT_IP, SERVICES_URL).joinToString(separator = "/")
        val requestQueue = Volley.newRequestQueue(context)
        getFBXApps(requestQueue, url, onSuccess, onFailure)
    }

    companion object {
        fun new(args: Bundle): LauncherFragment {
            val fragment = LauncherFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun injectFragment(appComponent: AppComponent) = appComponent.inject(this)
}
