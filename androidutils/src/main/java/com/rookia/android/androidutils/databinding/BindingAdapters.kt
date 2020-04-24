package com.rookia.android.androidutils.databinding

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.invisible
import com.rookia.android.androidutils.extensions.visible
import java.io.File


@Suppress("unused")
class BindingAdapters {

    companion object {

        @BindingAdapter(value = ["imageRounded", "defaultImage"], requireAll = false)
        @JvmStatic
        fun setImageUrlRounded(view: ImageView, url: String?, defaultImage: Drawable?) {
            //circle images
            val options = if (defaultImage == null) {
                RequestOptions()
                    .circleCrop()
            } else {
                RequestOptions()
                    .circleCrop()
                    .placeholder(defaultImage)
            }

            Glide.with(view.context)
                .load(url)
                .apply(
                    options
                )
                .into(view)
        }

        @JvmStatic
        @BindingAdapter(value = ["imageCenterAndCropped", "defaultImage"], requireAll = false)
        fun setImageUrlCenterAndCropped(view: ImageView, url: String?, defaultImage: Drawable?) {
            val options = if (defaultImage == null) {
                RequestOptions()
                    .centerCrop()
            } else {
                RequestOptions()
                    .centerCrop()
                    .placeholder(defaultImage)
            }
            //cropped images
            url?.let {
                Glide.with(view.context)
                    .load(url)
                    .apply(
                        options
                    )
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["imageCenterAndCropped", "defaultImage"], requireAll = false)
        fun setImageFileCenterAndCropped(view: ImageView, file: File?, defaultImage: Drawable?) {
            val options = if (defaultImage == null) {
                RequestOptions()
                    .centerCrop()
            } else {
                RequestOptions()
                    .centerCrop()
                    .placeholder(defaultImage)
            }
            //cropped images
            file?.let {
                Glide.with(view.context)
                    .load(file)
                    .apply(
                        options
                    )
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["imageCenterAndCropped", "defaultImage"], requireAll = false)
        fun setImageUriCenterAndCropped(view: ImageView, uri: Uri?, defaultImage: Drawable?) {
            val options = if (defaultImage == null) {
                RequestOptions()
                    .centerCrop()
            } else {
                RequestOptions()
                    .centerCrop()
                    .placeholder(defaultImage)
            }
            //cropped images
            uri?.let {
                Glide.with(view.context)
                    .load(uri)
                    .apply(
                        options
                    )
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("isVisibleOrGone")
        fun <T> setIsVisibleOrGone(view: View, isVisible: T?) {
            isVisible?.let {
                if (isVisible is Boolean && isVisible == true) {
                    view.visible()
                    return
                } else if (isVisible is String? && isVisible.isNotBlank()) {
                    view.visible()
                    return
                }
            }
            view.gone()
        }

        @JvmStatic
        @BindingAdapter("isVisibleOrInvisible")
        fun <T> setIsVisibleOrInvisible(view: View, isVisible: T?) {
            isVisible?.let {
                if (isVisible is Boolean && isVisible == true) {
                    view.visible()
                    return
                } else if (isVisible is String? && isVisible.isNotBlank()) {
                    view.visible()
                    return
                }
            }
            view.invisible()
        }

        @JvmStatic
        @BindingAdapter("isBoldOrNot")
        fun <T> setIsBoldOrNot(textView: TextView, isBold: T?) {
            isBold?.let {
                if (isBold is Boolean && isBold == true) {
                    textView.setTypeface(textView.typeface, Typeface.BOLD)
                    return
                }
            }
            textView.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
        }

        @JvmStatic
        @BindingAdapter("setBackground")
        fun <T> setBackground(view: View, background: T?) {
            background?.let {
                if (it is Int) {
                    view.setBackgroundResource(it)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("setBackgroundOnTextView")
        fun <T> setBackgroundOnTextView(textView: View, background: T?) {
            background?.let {
                if (it is Int) {
                    textView.setBackgroundResource(it)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("setBackgroundOnTextView")
        fun <T> setTextColorOnTextView(textView: TextView, color: T?) {
            color?.let {
                if (it is Int) {
                    textView.setTextColor(it)
                }
            }
        }

        @JvmStatic
        @Suppress("DEPRECATION")
        @BindingAdapter("htmlText")
        fun setHtmlText(textView: TextView, string: String?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY)
            } else {
                textView.text = Html.fromHtml(string)
            }
        }

    }
}