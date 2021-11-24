package com.datikaa.lorempicsum.feature.main_pager

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.datikaa.lorempicsum.databinding.FragmentMainBinding
import com.datikaa.lorempicsum.extension.onEachWithLifecycle
import com.datikaa.lorempicsum.extension.screenMetrics
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
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
//        binding?.mainRecyclerView?.layoutManager = FlexboxLayoutManager(context).apply {
//            flexDirection = FlexDirection.ROW
//            justifyContent = JustifyContent.FLEX_END
//        }
        binding?.mainRecyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}