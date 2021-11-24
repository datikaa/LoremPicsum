package com.datikaa.lorempicsum.feature.main_pager.paging
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.databinding.MainListItemBinding
import com.datikaa.lorempicsum.feature.main_pager.model.MainPagerItemModel
import com.datikaa.lorempicsum.feature.main_pager.model.url
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainPagerViewHolder(val binding: MainListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MainPagerItemModel?) {
        model ?: return // TODO: handle error nicer
        ViewCompat.setTransitionName(binding.imageView, "imageView_${model.id}")
        binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        Picasso.get().load(model.url).placeholder(R.drawable.loading_gif).into(binding.imageView, object : Callback {
            override fun onSuccess() {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            }

            override fun onError(e: Exception?) {
                Picasso.get().load(R.drawable.homer_doh).into(binding.imageView)
            }
        })
    }
}