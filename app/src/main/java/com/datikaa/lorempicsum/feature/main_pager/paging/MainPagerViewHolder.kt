package com.datikaa.lorempicsum.feature.main_pager.paging

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.feature.main_pager.model.MainPagerItemModel
import com.squareup.picasso.Picasso

class MainPagerViewHolder(private val binding: MainListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MainPagerItemModel) {
//        Picasso.get().load(model.url).placeholder(R.drawable.loading_gif).into(binding.imageView)
        binding.imageView.load(model.url) {
            crossfade(true)
            placeholder(R.drawable.loading_gif)
        }
    }
}