package com.datikaa.lorempicsum.extension

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsState
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

@BindingConversion
fun convertDetailsStateLayoutStateToId(layoutState: DetailsState.LayoutState): Int = when(layoutState) {
    DetailsState.LayoutState.Init -> R.id.init
    DetailsState.LayoutState.NoBlur -> R.id.no_blur
    DetailsState.LayoutState.ShowBlur -> R.id.show_blur
    DetailsState.LayoutState.Info -> R.id.info
}

@BindingAdapter("animatedVisibility")
fun View.setAnimatedVisibility(visibility: Int) {
    animateVisibility(visibility)
}

