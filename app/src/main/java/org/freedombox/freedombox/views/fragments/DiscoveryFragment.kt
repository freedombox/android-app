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

package org.freedombox.freedombox.views.fragments

import android.content.Context.NSD_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_discovery.*
import org.freedombox.freedombox.R
import org.freedombox.freedombox.components.AppComponent
import org.freedombox.freedombox.utils.storage.getSharedPreference
import org.freedombox.freedombox.views.activities.LauncherActivity
import org.freedombox.freedombox.views.adapter.DiscoveryListAdapter
import org.freedombox.freedombox.views.adapter.DiscoveryListAdapter.OnItemClickListener
import org.freedombox.freedombox.views.model.ConfigModel
import javax.inject.Inject

class DiscoveryFragment : BaseFragment() {

    private val TAG: String = DiscoveryFragment::class.java.simpleName

    private lateinit var discoveredBoxListAdapter: DiscoveryListAdapter

    private val discoveredBoxList = mutableListOf<String>()

    private var configuredBoxList = listOf<String>()

    private val discoveredPortList = mutableListOf<String>()

    private var configuredPortList = listOf<String>()

    private var configuredBoxSetupList = listOf<ConfigModel>()

    private val SERVICE = "_freedombox._tcp"

    @Inject lateinit var sharedPreferences: SharedPreferences

    private lateinit var nsdManager: NsdManager

    private lateinit var discoveryListener: FBXDiscoveryListener

    @Inject lateinit var gson: Gson

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nsdManager = activity!!.getSystemService(NSD_SERVICE) as NsdManager

        discoveryListener = FBXDiscoveryListener()

        nsdManager.discoverServices(SERVICE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)

        val configuredBoxesJSON = getSharedPreference(sharedPreferences,
                getString(R.string.default_box))


        configuredBoxesJSON?.let {
            configuredBoxSetupList += gson.
                    fromJson<List<ConfigModel>>(configuredBoxesJSON,
                            object : TypeToken<List<ConfigModel>>() {}.type)
            for (configModel in configuredBoxSetupList) {
                configuredBoxList += configModel.domain
                configuredPortList += "80"
            }

            configuredGroup.visibility = View.VISIBLE

            val configuredBoxListAdapter = DiscoveryListAdapter(activity!!.applicationContext,
                    configuredBoxList,
                    configuredPortList, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(activity, LauncherActivity::class.java)
                    intent.putExtra(getString(R.string.current_box), configuredBoxList[position])
                    startActivity(intent)
                }
            })
            configuredListView.layoutManager = LinearLayoutManager(activity)
            configuredListView.adapter = configuredBoxListAdapter
        }

        discoveredBoxListAdapter = DiscoveryListAdapter(activity!!.applicationContext,
                discoveredBoxList,
                discoveredPortList, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(activity, LauncherActivity::class.java)
                intent.putExtra(getString(R.string.current_box),
                        discoveredBoxList[position])
                startActivity(intent)
            }
        })
        discoveredListView.layoutManager = LinearLayoutManager(activity)
        discoveredListView.adapter = discoveredBoxListAdapter
    }

    companion object {
        fun new(args: Bundle): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_discovery

    override fun injectFragment(appComponent: AppComponent) = appComponent.inject(this)

    override fun onDestroy() {
        nsdManager.stopServiceDiscovery(discoveryListener)

        super.onDestroy()
    }

    inner class FBXResolveListener : NsdManager.ResolveListener {
        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            Log.e(TAG, "Resolve failed" + errorCode)
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.e(TAG, "Resolve Succeeded. " + serviceInfo)

            Log.d(TAG, serviceInfo.port.toString())
            Log.d(TAG, serviceInfo.host.toString())

            val portConfigured = configuredBoxSetupList.filter {
                it.domain == serviceInfo.host.toString()
            }
            if (portConfigured.isEmpty()) {
                discoveredBoxList.add(serviceInfo.host.toString())
                discoveredPortList.add(serviceInfo.port.toString())
            }
            activity!!.runOnUiThread {
                discoveredBoxListAdapter.notifyDataSetChanged()
            }

        }
    }

    inner class FBXDiscoveryListener : NsdManager.DiscoveryListener {
        override fun onServiceFound(serviceInfo: NsdServiceInfo) {
            Log.d(TAG, String.format("onServiceFound() serviceType %s", serviceInfo.serviceType))
            Log.d(TAG, String.format("onServiceFound() serviceName %s", serviceInfo.serviceName))
            discoveredBoxList.clear()
            discoveredPortList.clear()
            nsdManager.resolveService(serviceInfo, FBXResolveListener())
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode)
            nsdManager.stopServiceDiscovery(this)
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode)

            nsdManager.stopServiceDiscovery(this)
        }

        override fun onDiscoveryStarted(serviceType: String) {
            Log.d(TAG, "Service discovery started")
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: " + serviceType)
        }

        override fun onServiceLost(serviceInfo: NsdServiceInfo) {
            Log.e(TAG, "service lost" + serviceInfo)
        }
    }
}
