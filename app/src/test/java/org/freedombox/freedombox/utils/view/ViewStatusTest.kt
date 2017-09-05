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

package org.freedombox.freedombox.utils.view

import android.widget.EditText
import android.widget.Switch
import junit.framework.Assert
import org.freedombox.freedombox.BuildConfig
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class ViewStatusTest {

    val value = "test"

    @Test
    fun shouldReturnEnteredTextInTextBox() {
        var editText = EditText(RuntimeEnvironment.application)
        editText.setText(value)
        Assert.assertEquals(value, getEnteredText(editText))
    }

    @Test
    fun shouldReturnEmptyWhenNotEnteredText() {
        var editText = EditText(RuntimeEnvironment.application)
        Assert.assertEquals("", getEnteredText(editText))
    }

    @Test
    fun shouldReturnTrueWhenSwitchIsOn() {
        var switch = Switch(RuntimeEnvironment.application)
        switch.setChecked(true)
        Assert.assertTrue(getSwitchStatus(switch))
    }

    @Test
    fun shouldReturnFalseWhenSwitchIsOff() {
        var switch = Switch(RuntimeEnvironment.application)
        switch.setChecked(false)
        Assert.assertFalse(getSwitchStatus(switch))
    }

}
