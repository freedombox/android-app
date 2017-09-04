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

package org.freedombox.freedombox.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.freedombox.freedombox.R

class DiscoveryListAdapter(val context: Context, boxNameList: ArrayList<String>, portList: ArrayList<String>) : BaseAdapter() {

    private var boxNameList: ArrayList<String>
    private var portList: ArrayList<String>

    init {
        this.boxNameList = boxNameList
        this.portList = portList
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflaterView = convertView
        if (inflaterView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflaterView = inflater.inflate(R.layout.discovery_listview, null)
        }
        val boxName = inflaterView?.findViewById(R.id.boxname) as TextView
        val port = inflaterView.findViewById(R.id.port) as TextView
        boxName.text = boxNameList.get(position)
        port.text = portList.get(position)
        return inflaterView

    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return boxNameList.size
    }

}