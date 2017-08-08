/** This file is part of FreedomBox.
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

package org.freedombox.freedombox.View.Adapter

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.card.view.appDescription
import kotlinx.android.synthetic.main.card.view.appName
import org.freedombox.freedombox.BuildConfig
import org.freedombox.freedombox.Utils.ImageRenderer
import org.freedombox.freedombox.Views.Adapter.GridAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class GridAdapterTest {
    val applicationContext = application.applicationContext
    private val gridAdapter = GridAdapter(applicationContext, imageRenderer = ImageRenderer(applicationContext))
    private var jsonArray = JsonArray()
    private val jsonObject = JsonParser().parse("{'label': {'en': 'conversations'}, 'description': " +
            "{'en': 'someDescription'}, 'icon': 'value', 'color': '#FFFFFF'}").asJsonObject

    @Before
    fun setUp() {
        jsonArray.add(jsonObject)
    }

    @Test
    fun testItemCount() {
        gridAdapter.setData(jsonArray)
        assertEquals(gridAdapter.count, 1)
    }

    @Test
    fun testGetItemAtPosition() {
        gridAdapter.setData(jsonArray)
        assertEquals(gridAdapter.getItem(0), jsonObject)
    }

    @Test
    fun testViewIsGettingPopulated() {
        gridAdapter.setData(jsonArray)
        val view = gridAdapter.getView(0, null, null)
        assertEquals(view.appName.text.toString(), "conversations")
        assertEquals(view.appDescription.text.toString(), "someDescription")
    }
}
