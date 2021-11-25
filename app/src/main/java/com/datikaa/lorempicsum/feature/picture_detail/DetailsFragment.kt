package com.datikaa.lorempicsum.feature.picture_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.datikaa.lorempicsum.databinding.FragmentDetailsBinding
import com.datikaa.lorempicsum.extension.*
import com.google.android.material.internal.ViewUtils.doOnApplyWindowInsets
import com.google.android.material.slider.Slider
import kotlinx.coroutines.flow.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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

    @Suppress("DEPRECATION")
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding!!) {
            imageView.setCompatTransitionName("imageView_$id")

            onEachWithLifecycle(viewModel.state) {
                Log.d("teszt", it.toString())
                state = it
            }

            motionLayout.doOnApplyWindowInsets { v, windowInsets, initialPadding ->
                v.updatePadding(
                    bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom,
                )
            }

            buttonGroup.addOnButtonCheckedListener { _, checkedId, _ ->
                val intent = when (checkedId) {
                    buttonOriginal.id -> DetailsIntent.Original
                    buttonGreyScale.id -> DetailsIntent.GrayScale
                    buttonBlur.id -> DetailsIntent.Blur
                    else -> throw RuntimeException()
                }
                viewModel.submitIntent(intent)
            }

            slider.addOnChangeListener { _, value, fromUser ->
                if (!fromUser) return@addOnChangeListener
                viewModel.submitIntent(DetailsIntent.BlurValueChange(value.toBlurValue()))
            }
        }
    }
}