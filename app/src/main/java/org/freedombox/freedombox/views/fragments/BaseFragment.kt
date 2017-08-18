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

package org.freedombox.freedombox.views.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.freedombox.freedombox.applications.FreedomBoxApp
import org.freedombox.freedombox.components.AppComponent
import org.freedombox.freedombox.views.IBaseView

abstract class BaseFragment : android.support.v4.app.Fragment(), IBaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (activity.application as FreedomBoxApp).appComponent
        appComponent.inject(this)
        injectFragment(appComponent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(getLayoutId(), container, false)

    protected abstract fun getLayoutId(): Int

    protected abstract fun injectFragment(appComponent: AppComponent)

    override fun showSnackMessage(message: String, duration: Int) {
        Snackbar.make(
            activity.findViewById(android.R.id.content),
            message,
            duration
        ).show()
    }
}
