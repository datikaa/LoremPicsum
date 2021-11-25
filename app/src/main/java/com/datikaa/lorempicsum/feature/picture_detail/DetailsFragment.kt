package com.datikaa.lorempicsum.feature.picture_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.databinding.FragmentDetailsBinding
import com.datikaa.lorempicsum.extension.doOnApplyWindowInsets
import com.datikaa.lorempicsum.extension.onEachWithLifecycle
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Exception

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel { parametersOf(args.picsumArg.downloadUrl) }

    private var binding: FragmentDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false).apply {
            this.lifecycleOwner = this@DetailsFragment.viewLifecycleOwner
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val (id, url, downloadUrl) = args.picsumArg
        ViewCompat.setTransitionName(binding!!.imageView, "imageView_$id")
        Picasso.get().load(url).placeholder(R.drawable.loading_gif)
            .into(binding?.imageView, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                    Picasso.get().load(downloadUrl).noPlaceholder().into(binding?.imageView)
                }

                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                    findNavController().navigateUp()
                }
            })

        onEachWithLifecycle(viewModel.state) {
            binding?.state = it
            Log.d("teszt", it.toString())
        }

        binding?.buttonGroup?.doOnApplyWindowInsets { v, windowInsets, initialPadding ->
            v.updatePadding(
                bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom,
            )
        }

        binding?.buttonGroup?.addOnButtonCheckedListener { _, checkedId, _ ->
            val intent = when(checkedId) {
                R.id.button_original -> DetailsIntent.Original
                R.id.button_greyScale -> DetailsIntent.GrayScale
                R.id.button_blur -> DetailsIntent.Blur
                else -> throw RuntimeException()
            }
            viewModel.submitIntent(intent)
        }
    }
}