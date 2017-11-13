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
import org.freedombox.freedombox.BuildConfig
import org.freedombox.freedombox.views.adapter.DiscoveryListAdapter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class DiscoveryListAdapterTest {

    private val applicationContext: Context = RuntimeEnvironment.application.applicationContext
    private val boxNameList = mutableListOf<String>()
    private val portList = mutableListOf<String>()
    private lateinit var listAdapter: DiscoveryListAdapter
    private val boxName1 = "box1"
    private val boxName2 = "box2"
    private val portName1 = "port1"
    private val portName2 = "port2"

    @Before
    fun setUp() {
        boxNameList.add(boxName1)
        portList.add(portName1)
        boxNameList.add(boxName2)
        portList.add(portName2)
        listAdapter = DiscoveryListAdapter(applicationContext, boxNameList, portList, object : DiscoveryListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    @Test
    fun testItemCount() {
        Assert.assertEquals(listAdapter.itemCount, 2)
    }

    @Test
    fun testGetItemAtPositionZero() {
        Assert.assertEquals(listAdapter.getItemId(0), boxNameList[0])
    }

    @Test
    fun testGetItemIdAtPositionOne() {
        Assert.assertEquals(listAdapter.getItemId(1), boxNameList[1].hashCode().toLong())
    }

}
