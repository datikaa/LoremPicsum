package com.datikaa.lorempicsum.feature.main_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.feature.main_pager.model.MainItemModel

class MainAdapter : RecyclerView.Adapter<MainItemViewHolder>() {

    private val differ = AsyncListDiffer(this, MainAdapterItemDiffer)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val binding = MainListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(models: List<MainItemModel>) {
        differ.submitList(models)
    }
}

class MainItemViewHolder(private val binding: MainListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MainItemModel) {
        binding.imageView.load(model.url)
    }
}

private object MainAdapterItemDiffer : DiffUtil.ItemCallback<MainItemModel>() {

    override fun areItemsTheSame(oldItem: MainItemModel, newItem: MainItemModel): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MainItemModel, newItem: MainItemModel): Boolean = oldItem.url == newItem.url
}

