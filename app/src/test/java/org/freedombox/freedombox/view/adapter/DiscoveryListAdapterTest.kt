/* This file is part of FreedomBox.
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

package org.freedombox.freedombox.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import org.freedombox.freedombox.BuildConfig
import org.freedombox.freedombox.views.activities.DiscoveryActivity
import org.freedombox.freedombox.views.adapter.DiscoveryListAdapter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class DiscoveryListAdapterTest {

    @Inject
    private lateinit var activity: DiscoveryActivity

    @Inject
    private lateinit var aaa: LayoutInflater

    @Inject
    private lateinit var vvv: View

    private val applicationContext: Context = RuntimeEnvironment.application.applicationContext
    var boxNameList = ArrayList<String>()
    var portList = ArrayList<String>()
    private lateinit var listAdapter: DiscoveryListAdapter
    var item1 = "item1"
    var item2 = "item2"

    @Before
    fun setUp() {
        boxNameList.add(item1)
        portList.add(item2)
        listAdapter = DiscoveryListAdapter(applicationContext, boxNameList, portList)
    }

    @Test
    fun testItemCount() {
        Assert.assertEquals(listAdapter.count, 1)
    }

    @Test
    fun testGetItemAtPosition() {
        Assert.assertEquals(listAdapter.getItem(0), 0)
    }

    @Test
    fun testGetItemId() {
        Assert.assertEquals(listAdapter.getItemId(0), 0)
    }

}
