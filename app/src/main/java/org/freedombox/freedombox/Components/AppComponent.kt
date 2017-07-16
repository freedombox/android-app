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

package org.freedombox.freedombox.Components

import dagger.Component
import org.freedombox.freedombox.Applications.FreedomBoxApp
import org.freedombox.freedombox.Modules.AppModule
import org.freedombox.freedombox.Views.Fragments.BaseFragment
import org.freedombox.freedombox.Views.Fragments.SplashFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: FreedomBoxApp)

    fun inject(baseFragment: BaseFragment)

    fun inject(splashFragment: SplashFragment)
}
