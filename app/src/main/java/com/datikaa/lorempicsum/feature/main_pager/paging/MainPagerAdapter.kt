package com.datikaa.lorempicsum.feature.main_pager.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.extension.screenMetrics
import com.datikaa.lorempicsum.extension.toAdapterItem
import com.datikaa.lorempicsum.feature.main_pager.MainPagerFragmentDirections
import com.datikaa.lorempicsum.feature.main_pager.model.url
import com.datikaa.lorempicsum.feature.picture_detail.tools.DetailsFragmentPicsumArg

class MainPagerAdapter :
    PagingDataAdapter<PicsumPicture, MainPagerViewHolder>(MainPagerAdapterItemDiffer) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainListItemBinding.inflate(inflater, parent, false)
        return MainPagerViewHolder(binding).apply {
            this.binding.root.setOnClickListener {
                val item = getItem(bindingAdapterPosition)?.calculateSize()?.toAdapterItem() ?: return@setOnClickListener
                val extras = FragmentNavigatorExtras(this.binding.imageView to "imageView_${item.id}")
                val args = DetailsFragmentPicsumArg(item.id, item.url, item.downloadUrl)
                val action = MainPagerFragmentDirections.actionMainPagerFragmentToDetailsFragment(args)
                it.findNavController().navigate(action, extras)
            }
        }
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

