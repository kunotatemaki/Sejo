package com.rookia.android.androidutils.databinding

import android.graphics.Typeface
import android.net.Uri
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
        @BindingAdapter("imageRounded")
        fun setImageUrlRounded(view: ImageView, url: String?) {
            //circle images
            url?.let {
                Glide.with(view.context)
                    .load(url)
                    .apply(
                        RequestOptions()
                            .circleCrop()
                    )
                    .into(view)
            }
        }

        @BindingAdapter("imageAsId")
        fun setImageFromId(view: ImageView, id: Int?) {
            //circle images
            id?.let {
                Glide.with(view.context)
                    .load(id)
                    .into(view)
            }
        }


        @BindingAdapter("imageCenterCropped")
        fun setImageUrlCenterAndCropped(view: ImageView, url: String?) {
            //cropped images
            url?.let {
                Glide.with(view.context)
                    .load(url)
                    .apply(
                        RequestOptions()
                            .centerCrop()
                    )
                    .into(view)
            }
        }

        @BindingAdapter("imageCenterCropped")
        fun setImageFileCenterAndCropped(view: ImageView, file: File?) {
            //cropped images
            file?.let {
                Glide.with(view.context)
                    .load(file)
                    .apply(
                        RequestOptions()
                            .centerCrop()
                    )
                    .into(view)
            }
        }

        @BindingAdapter("imageCenterCropped")
        fun setImageUriCenterAndCropped(view: ImageView, uri: Uri?) {
            //cropped images
            uri?.let {
                Glide.with(view.context)
                    .load(uri)
                    .apply(
                        RequestOptions()
                            .centerCrop()
                    )
                    .into(view)
            }
        }

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

        @BindingAdapter("setBackground")
        fun <T> setBackground(view: View, background: T?) {
            background?.let {
                if (it is Int) {
                    view.setBackgroundResource(it)
                }
            }
        }

        @BindingAdapter("setBackgroundOnTextView")
        fun <T> setBackgroundOnTextView(textView: View, background: T?) {
            background?.let {
                if (it is Int) {
                    textView.setBackgroundResource(it)
                }
            }
        }

        @BindingAdapter("setBackgroundOnTextView")
        fun <T> setTextColorOnTextView(textView: TextView, color: T?) {
            color?.let {
                if (it is Int) {
                    textView.setTextColor(it)
                }
            }
        }
    }
}