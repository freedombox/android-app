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

import android.content.SharedPreferences
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_setup.*
import org.freedombox.freedombox.R
import org.freedombox.freedombox.components.AppComponent
import org.freedombox.freedombox.utils.storage.getSharedPreference
import org.freedombox.freedombox.utils.storage.putSharedPreference
import org.freedombox.freedombox.utils.view.getEnteredText
import org.freedombox.freedombox.utils.view.getSwitchStatus
import org.freedombox.freedombox.views.model.ConfigModel
import javax.inject.Inject

class SetupFragment : BaseFragment() {

    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val currentBoxName = activity!!.intent.getStringExtra(getString(R.string.current_box))
        discoveredUrl.setText(currentBoxName)

        saveConfig.setOnClickListener {
            storeEnteredDetailsInPreference()
            activity!!.finish()
        }

    }

    private fun storeEnteredDetailsInPreference() {
        val configuredBoxesJSON = getSharedPreference(sharedPreferences,
            getString(R.string.default_box))

        val configModel = ConfigModel(
            getEnteredText(boxName),
            getEnteredText(discoveredUrl),
            getEnteredText(username),
            getEnteredText(password),
            getSwitchStatus(setDefault))

        val configuredBoxList = (configuredBoxesJSON?.let {
            val gson = GsonBuilder().setPrettyPrinting().create()
            gson.fromJson<List<ConfigModel>>(it, object : TypeToken<List<ConfigModel>>() {}.type)
        } ?: listOf()).plus(configModel)

        putSharedPreference(sharedPreferences,
            getString(R.string.default_box),
            configuredBoxList)
    }

    override fun getLayoutId() = R.layout.fragment_setup

    companion object {
        fun new(args: Bundle): SetupFragment {
            val fragment = SetupFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun injectFragment(appComponent: AppComponent) = appComponent.inject(this)

}
