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

package org.freedombox.freedombox.utils

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.StreamEncoder
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import com.caverock.androidsvg.SVG
import org.freedombox.freedombox.R
import org.freedombox.freedombox.svg.SvgDecoder
import org.freedombox.freedombox.svg.SvgDrawableTranscoder
import java.io.InputStream

class ImageRenderer(val context: Context) {
    private val requestBuilder: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> = Glide
            .with(context)
            .using(Glide.buildStreamModelLoader(Uri::class.java, context), InputStream::class.java)
            .from(Uri::class.java)
            .`as`(SVG::class.java)
            .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
            .sourceEncoder(StreamEncoder())
            .cacheDecoder(FileToStreamDecoder<SVG>(SvgDecoder()))
            .decoder(SvgDecoder())
            .dontAnimate()
            .error(R.drawable.ic_logo)

    fun loadSvgImageFromURL(url: Uri, imageView: ImageView) {
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(url)
                .into(imageView)
    }

    fun loadImageFromURL(url: Uri, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_logo)
                .into(imageView)
    }
}
