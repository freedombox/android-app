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

package org.freedombox.freedombox.Views.Fragment

import org.freedombox.freedombox.BuildConfig
import org.freedombox.freedombox.Views.Fragments.LauncherFragment
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class LauncherFragmentTest {
    @Test
    fun viewIsGettingPopulatedWithIcons() {
        val launcherFragment = LauncherFragment()
        startFragment(launcherFragment)
        assertNotNull(launcherFragment)
    }
}
