package com.rookia.android.androidutils.utils

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.rookia.android.androidutils.data.resources.ResourcesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, April 2020
 *
 *
 */

class DeviceUtils @Inject constructor(@ApplicationContext private val context: Context, private val resourcesManager: ResourcesManager) {


    fun getScreenDimensions(): ScreenDimensions? {
        val displayMetrics = resourcesManager.getResources().displayMetrics
        return ScreenDimensions(displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.density)
    }

    fun getHeight(text: CharSequence, textView: TextView, deviceWidth: Int): Int {
        textView.text = text
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.EXACTLY)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        textView.measure(widthMeasureSpec, heightMeasureSpec)
        return textView.measuredHeight
    }

    fun getPxFromDp(dp: Float) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

    fun isPortrait(): Boolean =
        resourcesManager.getResources().configuration.orientation == Configuration.ORIENTATION_PORTRAIT


    class ScreenDimensions constructor(private val widthPx: Int, private val hightPx: Int, private val density: Float) {

        fun getWidthInPx(): Int {
            return widthPx
        }

        fun getHeightInPx(): Int {
            return hightPx
        }

        fun getWidthInDp(): Float {
            return widthPx / density
        }

        fun getHeightInDp(): Float {
            return hightPx / density
        }

        fun getDensity(): Float {
            return density
        }
    }
}