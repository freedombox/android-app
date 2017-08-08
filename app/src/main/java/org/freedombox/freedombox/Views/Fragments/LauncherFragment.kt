/**
 *  This file is part of FreedomBox.
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

package org.freedombox.freedombox.Views.Fragments

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_launcher.appGrid
import org.freedombox.freedombox.Components.AppComponent
import org.freedombox.freedombox.DEFAULT_FREEDOM_BOX_URL
import org.freedombox.freedombox.NetworkModule.AppLoader
import org.freedombox.freedombox.R
import org.freedombox.freedombox.SERVICES_FILE
import org.freedombox.freedombox.Utils.ImageRenderer
import org.freedombox.freedombox.Views.Adapter.GridAdapter
import javax.inject.Inject


class LauncherFragment : BaseFragment() {

    @Inject lateinit var imageRenderRenderer: ImageRenderer

    override fun getLayoutId() = R.layout.fragment_launcher

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = GridAdapter(activity.applicationContext, imageRenderRenderer)
        appGrid.adapter = adapter

        //TODO: Use the URL from settings once it is setup
        AppLoader(context, adapter).getFBXApps(SERVICES_FILE, DEFAULT_FREEDOM_BOX_URL)
    }

    companion object {
        fun new(args: Bundle): LauncherFragment {
            val fragment = LauncherFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun injectFragment(appComponent: AppComponent) = appComponent.inject(this)
}
