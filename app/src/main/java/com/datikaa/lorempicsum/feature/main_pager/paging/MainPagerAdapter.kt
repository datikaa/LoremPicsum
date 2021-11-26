package com.datikaa.lorempicsum.feature.main_pager.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.extension.calculateSize
import com.datikaa.lorempicsum.extension.toAdapterItem

class MainPagerAdapter(
    private val onItemClick: (picsumPicture: PicsumPicture) -> Unit,
) : PagingDataAdapter<PicsumPicture, MainPagerViewHolder>(MainPagerAdapterItemDiffer) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainListItemBinding.inflate(inflater, parent, false)
        return MainPagerViewHolder(binding).apply {
            this.binding.root.setOnClickListener {
                val item = getItem(bindingAdapterPosition) ?: return@setOnClickListener
//                FragmentNavigatorExtras(this.binding.imageView to "imageView_${item.id}")
                onItemClick(item)
            }
        }
    }

    override fun onBindViewHolder(holder: MainPagerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item?.calculateSize()?.toAdapterItem())
    }

    private object MainPagerAdapterItemDiffer : DiffUtil.ItemCallback<PicsumPicture>() {

        override fun areItemsTheSame(oldItem: PicsumPicture, newItem: PicsumPicture): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PicsumPicture, newItem: PicsumPicture): Boolean =
            oldItem.downloadUrl == newItem.downloadUrl
    }
}

