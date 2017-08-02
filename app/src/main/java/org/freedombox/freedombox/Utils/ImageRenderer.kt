package org.freedombox.freedombox.Utils

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
import org.freedombox.freedombox.SVGModules.SvgDecoder
import org.freedombox.freedombox.SVGModules.SvgDrawableTranscoder
import org.freedombox.freedombox.SVGModules.SvgSoftwareLayerSetter
import java.io.InputStream

class ImageRenderer(val context: Context) {
    lateinit var requestBuilder: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>

    fun init() {
        requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri::class.java, context), InputStream::class.java)
                .from(Uri::class.java)
                .`as`(SVG::class.java)
                .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
                .sourceEncoder(StreamEncoder())
                .cacheDecoder(FileToStreamDecoder<SVG>(SvgDecoder()))
                .decoder(SvgDecoder())
                .dontAnimate()
                .error(R.drawable.ic_logo)
                .listener(SvgSoftwareLayerSetter())
    }

    fun getImageFromUrl(url: Uri, imageView: ImageView) {
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(url)
                .into(imageView)
    }
}
