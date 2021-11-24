package com.datikaa.lorempicsum.feature.main_pager.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.extension.screenMetrics
import com.datikaa.lorempicsum.extension.toAdapterItem

class MainPagerAdapter :
    PagingDataAdapter<PicsumPicture, MainPagerViewHolder>(MainPagerAdapterItemDiffer) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainListItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainPagerFragment_to_detailsFragment)
        }
        return MainPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainPagerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item?.calculateSize()?.toAdapterItem())
    }

    /**
     * Recursively calculate image size by reducing it in 10% increments
     */
    private fun PicsumPicture.calculateSize(): PicsumPicture {
        return if (width > screenMetrics.width / 2) {
            copy(
                width = (width / 1.1).toInt(),
                height = (height / 1.1).toInt(),
            ).calculateSize()
        } else this
    }

    private object MainPagerAdapterItemDiffer : DiffUtil.ItemCallback<PicsumPicture>() {

        override fun areItemsTheSame(oldItem: PicsumPicture, newItem: PicsumPicture): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PicsumPicture, newItem: PicsumPicture): Boolean =
            oldItem.downloadUrl == newItem.downloadUrl
    }
}

