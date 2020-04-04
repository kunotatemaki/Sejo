package com.rookia.android.sejo.ui.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ComponentSejoButtonBinding


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

/**
 * Attributes available:<br></br><br></br>
 *
 *
 * app:onButtonClick        <br></br>
 * app:text                 <br></br>
 * app:image                <br></br>
 * app:activated            <br></br>
 * app:bnextButtonView      <br></br><br></br>
 * <u>THEMES:</u>           <br></br>
 * app:lightTheme           <br></br>
 * app:blueTheme            <br></br>
 * app:greenTheme           <br></br>
 * app:redTheme             <br></br>
 * app:lightBlueTheme       <br></br>
 * app:whiteTheme           <br></br>
 * app:greyTheme            <br></br>
 * app:pinkTheme            <br></br>
 * app:pinkBorderTheme      <br></br>
 * app:transparentTheme     <br></br>
 * app:colorsTheme          <br></br>
 *
 */
class SejoButtonView : FrameLayout {
    private lateinit var binding: ComponentSejoButtonBinding
    private var text: String = ""
    private var imageResId = -1
    private var isButtonActive = true
    private var clickListener: OnClickListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        initAttrs(context, attrs)
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
        init(context)
    }

    private fun initAttrs(
        context: Context,
        attrs: AttributeSet
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SejoButtonView,
            0,
            0
        )
        try {
            a.getString(R.styleable.SejoButtonView_text)?.let {
                text = it
            }
            isButtonActive = a.getBoolean(R.styleable.SejoButtonView_activated, true)
            imageResId = a.getResourceId(R.styleable.SejoButtonView_image, -1)
        } finally {
            a.recycle()
        }
    }

    private fun init(context: Context) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ComponentSejoButtonBinding.inflate(inflater, this, true)
        setText(text, false)
        if (imageResId != -1) {
            setImage(imageResId)
        }
        isEnabled = isButtonActive
    }

    fun setGreenTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_green
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.minimal
            )
        )
    }

    fun setRedTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_red
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.minimal
            )
        )
    }

    fun setLightTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_light
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.error
            )
        )
    }

    fun setBlueTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_blue
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.minimal
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.BOLD
        )
    }

    fun setLightBlueTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_lightblue
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.blue
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.BOLD
        )
    }

    fun setWhiteTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_white
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.grey_5
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.BOLD
        )
    }

    fun setOrangeTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_orange
        )
    }

    fun setRejectTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_reject
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.error
            )
        )
    }

    fun setGreyTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_grey
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.grey_3
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.BOLD
        )
    }

    fun setPinkTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.minimal
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.NORMAL
        )
    }

    fun setPinkBorderTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_pink_border
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.candy
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.BOLD
        )
    }

    fun setTransparentTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_transparent
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.candy
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.BOLD
        )
    }

    fun setColorsTheme() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_colors
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.minimal
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.BOLD
        )
    }

    fun setWhiteBackgroundWithCandyTextColor() {
        binding.componentSejoButtonContainer.background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_component_sejo_button_white
        )
        binding.componentSejoButtonButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.candy
            )
        )
        binding.componentSejoButtonButton.setTypeface(
            binding.componentSejoButtonButton.typeface,
            Typeface.BOLD
        )
    }

    /**
     * Set button image and visualize it, image will be resized to (24dp * 24dp)
     * @param resId
     */
    fun setImage(resId: Int) {
        binding.componentSejoButtonButton.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(context, resId),
            null,
            null,
            null
        )
    }

    /**
     * Set button text without animating it
     * @param text
     */
    fun setText(text: String?) {
        setText(text, false)
    }

    /**
     * Set button text without animating it
     * @param resId
     */
    fun setText(resId: Int) {
        setText(resId, false)
    }

    fun setText(text: String?, animate: Boolean) {
        binding.componentSejoButtonButton.text = text
        if (animate) {
            binding.componentSejoButtonButton.startAnimation(
                fadeIn(300)
            )
        }
    }

    fun setText(resId: Int, animate: Boolean) {
        binding.componentSejoButtonButton.text = context.getString(resId)
        if (animate) {
            binding.componentSejoButtonButton.startAnimation(
                fadeIn(300)
            )
        }
    }

    fun setSejoOnClickListener(listener: OnClickListener?) {

        //Do not save the listener in case we passed a null
        //since this means we are disabling it.
        if (listener != null) {
            clickListener = listener
        }
        if (isButtonActive) {
            binding.componentSejoButtonContainer.setOnClickListener(listener)
        } else {
            binding.componentSejoButtonContainer.setOnClickListener(null)
        }
    }

    override fun setEnabled(setEnabled: Boolean) {
        isButtonActive = setEnabled
        if (setEnabled) {
            binding.componentSejoButtonContainer.setBackgroundResource(R.drawable.bg_component_sejo_button)
            setSejoOnClickListener(clickListener)
        } else {
            binding.componentSejoButtonContainer.setBackgroundResource(R.drawable.bg_component_sejo_button_disabled)
            setSejoOnClickListener(null)
        }
    }

    fun setActive(setActive: Boolean) {
        isButtonActive = setActive
        if (setActive) {
            setSejoOnClickListener(clickListener)
        } else {
            setSejoOnClickListener(null)
        }
        binding.componentSejoButtonContainer.isFocusable = setActive
        binding.componentSejoButtonContainer.isClickable = setActive
    }

    private fun fadeIn(duration: Int): Animation {
        animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = duration.toLong()
        animation.startOffset = 0
        animation.fillAfter = true
        return animation
    }
}
 