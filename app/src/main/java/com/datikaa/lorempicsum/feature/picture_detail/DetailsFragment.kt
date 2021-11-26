package com.datikaa.lorempicsum.feature.picture_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.databinding.FragmentDetailsBinding
import com.datikaa.lorempicsum.extension.*
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsDestination
import com.datikaa.lorempicsum.feature.picture_detail.dynamics.DetailsIntent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel { parametersOf(args.picsumArg) }

    private var binding: FragmentDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // I don't like the animation, also it's a bit buggy with the MotionLayout
//        sharedElementEnterTransition = inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false).apply {
            this.lifecycleOwner = this@DetailsFragment.viewLifecycleOwner
            this.state = viewModel.state.value
            binding = this
        }.root
    }

    @Suppress("DEPRECATION")
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding!!) {
            imageView.setCompatTransitionName("imageView_${args.picsumArg.id}")

            motionLayout.doOnApplyWindowInsets { v, windowInsets, initialPadding ->
                v.updatePadding(
                    top = initialPadding.top + windowInsets.systemWindowInsetTop,
                    bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom,
                    left = initialPadding.left + windowInsets.systemWindowInsetLeft,
                    right = initialPadding.right + windowInsets.systemWindowInsetRight,
                )
            }

            infoButton.doOnApplyWindowInsets { v, windowInsets, initialPadding ->
                v.updatePadding(
                    top = initialPadding.top + windowInsets.systemWindowInsetTop,
                    bottom = initialPadding.bottom + windowInsets.systemWindowInsetBottom,
                    left = initialPadding.left + windowInsets.systemWindowInsetLeft,
                    right = initialPadding.right + windowInsets.systemWindowInsetRight,
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

            infoButton.setOnClickListener {
                viewModel.submitIntent(DetailsIntent.Info)
            }

            infoCardButton.setOnClickListener {
                viewModel.submitIntent(DetailsIntent.CloseInfo)
            }

            infoCardShareButton.setOnClickListener {
                viewModel.submitIntent(DetailsIntent.Share)
            }

            onEachWithLifecycle(viewModel.state) {
                state = it
            }

            onEachWithLifecycle(slider.valueChangeFlow()) { (_, value, fromUser) ->
                if (!fromUser) return@onEachWithLifecycle
                viewModel.submitIntent(DetailsIntent.BlurValueChange(value.toBlurValue()))
            }

            onEachWithLifecycle(viewModel.navigationFlow) {
                when (it) {
                    is DetailsDestination.Share -> sharePicture(it.url)
                }
            }
        }
    }

    private fun sharePicture(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
        startActivity(Intent.createChooser(intent, getString(R.string.text_share_picture)))
    }
}