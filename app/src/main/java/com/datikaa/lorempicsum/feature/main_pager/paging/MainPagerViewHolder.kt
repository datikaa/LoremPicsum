package com.datikaa.lorempicsum.feature.main_pager.paging

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.feature.main_pager.model.MainItemModel

class MainPagerViewHolder(private val binding: MainListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MainItemModel) {
        binding.imageView.load(model.url) {
            crossfade(true)
            placeholder(R.drawable.loading_gif)
        }
    }
}