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

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_splash.btnSplashNext
import org.freedombox.freedombox.R
import org.freedombox.freedombox.components.AppComponent
import org.freedombox.freedombox.utils.storage.getSharedPreference
import org.freedombox.freedombox.views.activities.DiscoveryActivity
import org.freedombox.freedombox.views.activities.LauncherActivity
import org.freedombox.freedombox.views.model.ConfigModel
import javax.inject.Inject

class SplashFragment : BaseFragment() {
    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun getLayoutId() = R.layout.fragment_splash

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        openLauncherIfConfigured()

        btnSplashNext.setOnClickListener {
            val intent = Intent(activity, DiscoveryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openLauncherIfConfigured() {
        val configuredBoxesJSON = getSharedPreference(sharedPreferences,
                getString(R.string.default_box))

        configuredBoxesJSON?.let {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val configuredBoxList = gson.fromJson<ArrayList<ConfigModel>>(it,
                    object : TypeToken<List<ConfigModel>>() {}.type)
            val intent = Intent(activity, LauncherActivity::class.java)
            intent.putParcelableArrayListExtra(getString(R.string.current_box), configuredBoxList)
            startActivity(intent)
        }

    }

    companion object {
        fun new(args: Bundle): SplashFragment {
            val fragment = SplashFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun injectFragment(appComponent: AppComponent) = appComponent.inject(this)
}
