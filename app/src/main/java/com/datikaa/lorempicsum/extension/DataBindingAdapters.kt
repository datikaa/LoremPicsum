package com.datikaa.lorempicsum.extension

import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.datikaa.lorempicsum.feature.picture_detail.tools.BlurValue
import com.google.android.material.slider.Slider
import com.squareup.picasso.Picasso

@BindingAdapter("picassoUrl")
fun ImageView.picassoUrl(url: String?) {
    url ?: return
    Picasso.get().load(url).noPlaceholder().into(this)
}

@BindingAdapter("transitionState")
fun MotionLayout.transitionState(state: Int) {
    if (currentState == state) return
    transitionToState(state)
}

@BindingAdapter("blurValue")
fun Slider.setBlurValue(blurValue: BlurValue) {
    value = blurValue.value.toFloat()
}

@BindingConversion
fun convertBlurValueToInt(blurValue: BlurValue): Float = blurValue.value.toFloat()

