package com.datikaa.lorempicsum.feature.main_pager.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.extension.toAdapterItem

class MainPagerAdapter :
    PagingDataAdapter<PicsumPicture, MainPagerViewHolder>(MainPagerAdapterItemDiffer) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPagerViewHolder {
        val binding =
            MainListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainPagerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.toAdapterItem())
    }

    private object MainPagerAdapterItemDiffer : DiffUtil.ItemCallback<PicsumPicture>() {

        override fun areItemsTheSame(oldItem: PicsumPicture, newItem: PicsumPicture): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PicsumPicture, newItem: PicsumPicture): Boolean =
            oldItem.downloadUrl == newItem.downloadUrl
    }
}

