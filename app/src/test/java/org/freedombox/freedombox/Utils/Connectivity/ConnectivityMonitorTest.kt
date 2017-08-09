/**
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

package org.freedombox.freedombox.Utils.Connectivity

import android.content.Intent
import android.net.ConnectivityManager
import org.freedombox.freedombox.BuildConfig
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class ConnectivityMonitorTest {

    @Test
    fun testSubscriber() {
        val monitor = ConnectivityMonitor(application.applicationContext)

        monitor.subscribe {
            assertTrue(true)
        }

        val intent = Intent(ConnectivityManager.CONNECTIVITY_ACTION)
        application.sendBroadcast(intent)
    }
}
