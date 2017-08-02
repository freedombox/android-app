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

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.card.view.*
import kotlinx.android.synthetic.main.fragment_launcher.*
import org.freedombox.freedombox.Components.AppComponent
import org.freedombox.freedombox.DEFAULT_FREEDOM_BOX_URL
import org.freedombox.freedombox.NetworkModule.getFBXApps
import org.freedombox.freedombox.R
import org.freedombox.freedombox.SERVICES_FILE
import org.freedombox.freedombox.Utils.ImageRenderer
import java.util.*
import javax.inject.Inject


class LauncherFragment : BaseFragment() {

    @Inject lateinit var imageRenderRenderer: ImageRenderer

    override fun getLayoutId() = R.layout.fragment_launcher

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //TODO: Use the URL from settings once it is setup
        val freedomboxUrl = DEFAULT_FREEDOM_BOX_URL
        val services = getFBXApps(SERVICES_FILE, activity.applicationContext, freedomboxUrl)
        app_grid.adapter = GridAdapter(context, services)
    }

    companion object {
        fun new(args: Bundle): LauncherFragment {
            val fragment = LauncherFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun injectFragment(appComponent: AppComponent) = appComponent.inject(this)

    inner class GridAdapter(val context: Context, val items: JsonArray) : BaseAdapter() {

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return items[position].hashCode().toLong()
        }

        override fun getCount(): Int {
            return items.size()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView = inflater.inflate(R.layout.card, null)

            val appDetail = items[position].asJsonObject

            val locale = Locale.getDefault()
            rowView.appName.text = appDetail["label"]
                    .asJsonObject[locale.language]
                    .asString
            rowView.appDescription.text = appDetail["description"]
                    .asJsonObject[locale.language]
                    .asString

            imageRenderRenderer.getImageFromUrl(
                    Uri.parse(appDetail["icon"].asString),
                    rowView.appIcon
            )

            rowView.appIcon.setOnClickListener {}

            rowView.cardHolder.setBackgroundColor(Color.parseColor(appDetail["color"].asString))

            return rowView
        }
    }
}
