package com.rookia.android.sejo.ui.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ComponentBulletsTextViewBinding
import com.rookia.android.sejo.utils.VibrationUtils


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

class BulletsTextView : ConstraintLayout {
    private lateinit var binding: ComponentBulletsTextViewBinding
    private var content = ""
    private var listener: OnTextChangedListener? = null

    interface OnTextChangedListener {
        fun onText(newText: String)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ComponentBulletsTextViewBinding.inflate(inflater, this, true)

    }

    private fun setTextInBullets() {
        for (i in 0 until PASSWORD_LENGTH) {
            val char = if (i < content.length) content[i] else null
            val textView: TextView
            val imageView: ImageView
            when (i) {
                0 -> {
                    imageView = binding.componentBulletsTextview0
                    textView = binding.componentBulletsTextviewText0
                }
                1 -> {
                    imageView = binding.componentBulletsTextview1
                    textView = binding.componentBulletsTextviewText1
                }
                2 -> {
                    imageView = binding.componentBulletsTextview2
                    textView = binding.componentBulletsTextviewText2
                }
                else -> {
                    imageView = binding.componentBulletsTextview3
                    textView = binding.componentBulletsTextviewText3
                }
            }
            setCharInBullet(char, imageView, textView)
        }
    }

    private fun setCharInBullet(char: Char?, imageView: ImageView, textView: TextView) {
        if (char != null) {
            textView.text = char.toString()
            ImageViewCompat.setImageTintList(
                imageView,
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.password_bullet_pressed
                    )
                )
            );
        } else {
            ImageViewCompat.setImageTintList(
                imageView,
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.password_bullet_normal
                    )
                )
            );
        }
    }


    fun setOnTextChangedListener(listener: OnTextChangedListener?) {
        this.listener = listener
    }

    /**
     * Adds a new char to the string
     * and prints the bullets.
     */
    fun addChar(c: String) {
        if (content.length < PASSWORD_LENGTH) {
            content += c
            setText(content)
        }
    }

    /**
     * Deletes the last char of the string
     * and prints the bullets.
     */
    fun deleteChar() {
        if (content.isNotEmpty()) {
            content = content.dropLast(1)
            setText(content)
        }
    }

    /**
     * Set text, won't be allow to be more than the limit
     * and will be clipped if so.
     * Displays the correct bullets.
     * @param text
     */
    fun setText(text: String) {
        content = if (text.length <= PASSWORD_LENGTH) {
            text
        } else {
            text.substring(0, PASSWORD_LENGTH)
        }
        setTextInBullets()
        listener?.onText(content)

    }

    /**
     * @return the stored text
     */
    fun getText(): String {
        return content
    }

    private fun animateShow(dotView: ImageView, text: TextView, position: Int) {
        dotView.animate()
            .alpha(0f)
            .rotationX(90f)
            .setDuration(PASSWORD_SHOW_HIDE_DURATION)
            .setStartDelay(position.toLong() * PASSWORD_SHOW_HIDE_DELAY)
            .startDelay
        text.animate()
            .alpha(1f)
            .rotationX(0f)
            .setDuration(PASSWORD_SHOW_HIDE_DURATION)
            .setStartDelay(position.toLong() * PASSWORD_SHOW_HIDE_DELAY + PASSWORD_SHOW_HIDE_DURATION)
            .startDelay
    }

    private fun animateHide(dotView: ImageView, text: TextView, position: Int) {
        text.animate()
            .alpha(0f)
            .rotationX(-90f)
            .setDuration(PASSWORD_SHOW_HIDE_DURATION)
            .setStartDelay(position.toLong() * PASSWORD_SHOW_HIDE_DELAY)
            .startDelay
        dotView.animate()
            .alpha(1f)
            .rotationX(0f)
            .setDuration(PASSWORD_SHOW_HIDE_DURATION)
            .setStartDelay(position.toLong() * PASSWORD_SHOW_HIDE_DELAY + PASSWORD_SHOW_HIDE_DURATION)
            .startDelay
    }

    fun showPassword() {
        with(binding) {
            animateShow(componentBulletsTextview0, componentBulletsTextviewText0, 1)
            animateShow(componentBulletsTextview1, componentBulletsTextviewText1, 2)
            animateShow(componentBulletsTextview2, componentBulletsTextviewText2, 3)
            animateShow(componentBulletsTextview3, componentBulletsTextviewText3, 4)
        }
    }

    fun hidePassword() {
        with(binding) {
            animateHide(componentBulletsTextview0, componentBulletsTextviewText0, 1)
            animateHide(componentBulletsTextview1, componentBulletsTextviewText1, 2)
            animateHide(componentBulletsTextview2, componentBulletsTextviewText2, 3)
            animateHide(componentBulletsTextview3, componentBulletsTextviewText3, 4)
        }
    }

    fun showErrorFeedback() {
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
        VibrationUtils.patternVibrate(context, Constants.ERROR_VIBRATION_PATTERN)
    }

    companion object {
        private const val PASSWORD_LENGTH: Int = 4
        private const val PASSWORD_SHOW_HIDE_DURATION: Long = 100L
        private const val PASSWORD_SHOW_HIDE_DELAY: Long = 50L
    }
}
