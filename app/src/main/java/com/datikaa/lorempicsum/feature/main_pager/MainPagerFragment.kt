package com.datikaa.lorempicsum.feature.main_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.datikaa.lorempicsum.databinding.FragmentMainBinding
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.extension.calculateSize
import com.datikaa.lorempicsum.extension.calculatedUrl
import com.datikaa.lorempicsum.extension.doOnApplyWindowInsets
import com.datikaa.lorempicsum.extension.onEachWithLifecycle
import com.datikaa.lorempicsum.feature.main_pager.dynamics.MainPagerIntent
import com.datikaa.lorempicsum.feature.main_pager.dynamics.MainPagerNavigation
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerAdapter
import com.datikaa.lorempicsum.feature.picture_detail.tools.DetailsFragmentPicsumArg
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainPagerFragment : Fragment() {

    private val viewModel: MainPagerViewModel by viewModel()
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

        val adapter = MainPagerAdapter() {
            val intent = MainPagerIntent.ShowDetails(it)
            viewModel.submitIntent(intent)
        }

        binding?.mainRecyclerView?.layoutManager =
            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding?.mainRecyclerView?.adapter = adapter

        binding?.mainRecyclerView?.doOnApplyWindowInsets { v, windowInsets, initialPadding ->
            v.updatePadding(
                top = initialPadding.top + windowInsets.systemWindowInsetTop,
                bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom,
                left = initialPadding.left + windowInsets.systemWindowInsetLeft,
                right = initialPadding.right + windowInsets.systemWindowInsetRight,
            )
        }

        onEachWithLifecycle(viewModel.navigationFlow) {
            when (it) {
                is MainPagerNavigation.ToDetails -> navigateToDetails(it.picsumPicture)
            }
        }

        onEachWithLifecycle(viewModel.pagerDataFlow) {
            adapter.submitData(it)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun navigateToDetails(picsumPicture: PicsumPicture) {
        val args =
            DetailsFragmentPicsumArg(
                picsumPicture.id,
                picsumPicture.calculateSize().calculatedUrl,
                picsumPicture.downloadUrl
            )
        val action = MainPagerFragmentDirections.actionMainPagerFragmentToDetailsFragment(args)
        findNavController().navigate(action)
    }
}