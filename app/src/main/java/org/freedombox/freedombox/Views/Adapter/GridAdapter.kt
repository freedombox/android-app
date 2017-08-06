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

package org.freedombox.freedombox.Views.Adapter

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.card.view.*
import org.freedombox.freedombox.R
import org.freedombox.freedombox.Utils.ImageRenderer
import java.util.*

class GridAdapter(val context: Context,val imageRenderer: ImageRenderer): BaseAdapter() {

    private var items:JsonArray = JsonArray()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.card, null)

        val appDetail = items[position].asJsonObject

        val locale = Locale.getDefault()
        rowView.appName.text = appDetail["label"]
                .asJsonObject[locale.language]
                .asString
        print(rowView.appName.text.toString())
        rowView.appDescription.text = appDetail["description"]
                .asJsonObject[locale.language]
                .asString

        imageRenderer.getImageFromUrl(
                Uri.parse(appDetail["icon"].asString),
                rowView.appIcon
        )

        rowView.appIcon.setOnClickListener {}

        rowView.cardHolder.setBackgroundColor(Color.parseColor(appDetail["color"].asString))

        return rowView
    }

    override fun getItem(position: Int): JsonObject {
        return items[position].asJsonObject
    }

    override fun getItemId(position: Int): Long {
        return items[position].hashCode().toLong()
    }

    override fun getCount(): Int {
        return items.size()
    }

    fun setData(jsonArry: JsonArray) {
        items = jsonArry
    }
}
