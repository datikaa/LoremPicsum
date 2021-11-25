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
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.databinding.FragmentDetailsBinding
import com.datikaa.lorempicsum.extension.doOnApplyWindowInsets
import com.datikaa.lorempicsum.extension.inflateTransition
import com.datikaa.lorempicsum.extension.onEachWithLifecycle
import com.datikaa.lorempicsum.extension.setCompatTransitionName
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Exception

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel { parametersOf(args.picsumArg) }

    private var binding: FragmentDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = inflateTransition(android.R.transition.move)
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

        with(binding!!) {
            imageView.setCompatTransitionName("imageView_$id")

            onEachWithLifecycle(viewModel.state) {
                Log.d("teszt", it.toString())
                state = it
            }

            buttonGroup.doOnApplyWindowInsets { v, windowInsets, initialPadding ->
                v.updatePadding(
                    bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom,
                )
            }

            buttonGroup.addOnButtonCheckedListener { _, checkedId, _ ->
                val intent = when(checkedId) {
                    buttonOriginal.id -> DetailsIntent.Original
                    buttonGreyScale.id -> DetailsIntent.GrayScale
                    buttonBlur.id -> DetailsIntent.Blur
                    else -> throw RuntimeException()
                }
                viewModel.submitIntent(intent)
            }
        }
    }
}