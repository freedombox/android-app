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

package org.freedombox.freedombox.Applications

import android.app.Application
import org.freedombox.freedombox.Components.AppComponent
import org.freedombox.freedombox.Components.DaggerAppComponent
import org.freedombox.freedombox.Modules.AppModule
import org.freedombox.freedombox.Utils.Connectivity.ConnectivityMonitor
import javax.inject.Inject

class FreedomBoxApp : Application() {
    lateinit var appComponent: AppComponent

    @Inject lateinit var connectivityMonitor: ConnectivityMonitor

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()

        appComponent.inject(this)
        connectivityMonitor.init()
    }
}
