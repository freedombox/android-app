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

package org.freedombox.freedombox.view.fragments

import android.content.Intent
import android.preference.PreferenceManager
import android.view.View
import org.freedombox.freedombox.BuildConfig
import org.freedombox.freedombox.R
import org.freedombox.freedombox.views.activities.DiscoveryActivity
import org.freedombox.freedombox.views.activities.LauncherActivity
import org.freedombox.freedombox.views.activities.MainActivity
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class SplashFragmentTest {
    val key = "default_box"
    val applicationContext = RuntimeEnvironment.application.applicationContext
    val sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(applicationContext)

    @After
    fun destroy() {
        sharedPreferences.edit().clear().commit()
    }

    @Test
    fun displayButtonWhenNoBoxConfigured() {

        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val shadowActvity = Shadows.shadowOf(activity)
        val buttonView = shadowActvity.findViewById(R.id.btnSplashNext)
        Assert.assertEquals(View.VISIBLE, buttonView.visibility)
    }


    @Test
    fun navigateToLauncherScreenWhenBoxConfigured() {

        val value = """
            [{
	            "boxName": "box",
	            "default": true,
	            "domain": "/10.42.0.1",
	            "password": "pass",
	            "username": "user"
            }]
        """

        sharedPreferences.edit().putString(key, value).commit()
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val shadowActvity = Shadows.shadowOf(activity)
        val expectedIntent = Intent(activity, LauncherActivity::class.java)
        Assert.assertEquals(expectedIntent.javaClass,
            shadowActvity.nextStartedActivity.javaClass)

    }

    @Test
    fun navigateToLauncherScreenOnButtonClick() {

        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val shadowActvity = Shadows.shadowOf(activity)
        val expectedIntent = Intent(activity, DiscoveryActivity::class.java)
        shadowActvity.findViewById(R.id.btnSplashNext).performClick()
        Assert.assertEquals(expectedIntent.javaClass,
            shadowActvity.nextStartedActivity.javaClass)
    }

}
