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

package org.freedombox.freedombox.SVGModules

import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import java.io.IOException
import java.io.InputStream

public class SvgDecoder: ResourceDecoder<InputStream, SVG> {

    override fun decode(source: InputStream?, width: Int, height: Int): Resource<SVG> {
        try {
            val svg = SVG.getFromInputStream(source)
            return SimpleResource<SVG>(svg)
        } catch (e: SVGParseException) {
            throw IOException("Cannot load SVG")
        }
    }

    override fun getId(): String {
        return "org.freedombox.freedombox.svg"
    }
}
