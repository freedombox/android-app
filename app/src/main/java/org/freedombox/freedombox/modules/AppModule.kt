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

package org.freedombox.freedombox.modules

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import org.freedombox.freedombox.utils.ImageRenderer
import org.freedombox.freedombox.utils.network.ConnectivityMonitor
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {
    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(application.applicationContext)

    @Provides
    @Singleton
    fun provideConnectivityMonitor(): ConnectivityMonitor =
        ConnectivityMonitor(application.applicationContext)

    @Provides
    @Singleton
    fun provideImageRenderer(): ImageRenderer =
        ImageRenderer(application.applicationContext)

    @Provides
    fun provideGson() : Gson = GsonBuilder().setPrettyPrinting().create();
}
