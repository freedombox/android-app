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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

class ConnectivityMonitor(applicationContext: Context) {
    private val connectivityManager: ConnectivityManager = applicationContext
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val subscribers = mutableListOf<(Boolean) -> Unit>()

    private val networkReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            notifySubscribers(isNetworkConnected())
        }
    }

    init {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        applicationContext.registerReceiver(networkReceiver, intentFilter)
    }

    fun subscribe(subscriber: (Boolean) -> Unit) {
        if (!subscribers.contains(subscriber))
            subscribers.add(subscriber)
    }

    fun unSubscribe(subscriber: (Boolean) -> Unit) {
        subscribers.remove(subscriber)
    }

    fun notifySubscribers(state: Boolean) {
        subscribers.forEach {
            it(state)
        }
    }

    fun isNetworkConnected(): Boolean {
        val info = connectivityManager.activeNetworkInfo
        return info != null && info.isConnected
    }
}
