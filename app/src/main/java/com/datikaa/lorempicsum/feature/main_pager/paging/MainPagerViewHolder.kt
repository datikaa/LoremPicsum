package com.datikaa.lorempicsum.feature.main_pager.paging
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.feature.main_pager.model.MainPagerItemModel
import com.datikaa.lorempicsum.feature.main_pager.model.url
import com.squareup.picasso.Picasso

class MainPagerViewHolder(val binding: MainListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MainPagerItemModel?) {
        model ?: return // TODO: handle error nicer
        ViewCompat.setTransitionName(binding.imageView, "imageView_${model.id}")
        Picasso.get().load(model.url).placeholder(R.drawable.loading_gif).into(binding.imageView)
    }
}