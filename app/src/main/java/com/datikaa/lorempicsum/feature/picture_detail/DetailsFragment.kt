package com.datikaa.lorempicsum.feature.picture_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.datikaa.lorempicsum.databinding.FragmentDetailsBinding
import com.datikaa.lorempicsum.feature.main_pager.MainPagerFragment

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }
}