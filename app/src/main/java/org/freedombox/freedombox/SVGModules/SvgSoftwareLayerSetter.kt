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

import android.graphics.drawable.PictureDrawable
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.Target
import java.lang.Exception

public class SvgSoftwareLayerSetter<T>: RequestListener<T, PictureDrawable> {
    override fun onResourceReady(resource: PictureDrawable?, model: T, target: Target<PictureDrawable>?, isFirstResource: Boolean, isMemoryCache: Boolean): Boolean {
        val view = (target as ImageViewTarget<*>).view
        if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
            view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
        }
        return false
    }

    override fun onException(exception: Exception?, model: T, target: Target<PictureDrawable>?, isFirstResource: Boolean): Boolean {
        val view = (target as ImageViewTarget<*>).view
        if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
            view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
        }
        return false
    }
}
