package com.datikaa.lorempicsum

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("picassoUrl")
fun ImageView.picassoUrl(url: String?) {
    url ?: return
    Picasso.get().load(url).noPlaceholder().into(this)
}
