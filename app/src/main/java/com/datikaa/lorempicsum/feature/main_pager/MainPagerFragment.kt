package com.datikaa.lorempicsum.feature.main_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.datikaa.lorempicsum.databinding.FragmentMainBinding
import com.datikaa.lorempicsum.extension.doOnApplyWindowInsets
import com.datikaa.lorempicsum.extension.onEachWithLifecycle
import com.datikaa.lorempicsum.extension.screenMetrics
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainPagerFragment : Fragment() {

    companion object {
        fun newInstance() = MainPagerFragment()
    }

    private val viewModel: MainPagerViewModel by viewModel { parametersOf(screenMetrics) }

    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainPagerAdapter()

        onEachWithLifecycle(viewModel.flow) {
            adapter.submitData(it)
        }

        binding?.mainRecyclerView?.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding?.mainRecyclerView?.adapter = adapter

        binding?.mainRecyclerView?.doOnApplyWindowInsets { v, windowInsets, initialPadding ->
            v.updatePadding(
                top = initialPadding.top + windowInsets.systemWindowInsetTop,
                bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom,
            )
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}