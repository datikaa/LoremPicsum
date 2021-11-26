package com.datikaa.lorempicsum.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import com.datikaa.lorempicsum.R
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.feature.main_pager.paging.MainPagerItemModel
import com.datikaa.lorempicsum.feature.picture_detail.tools.BlurValue
import com.datikaa.lorempicsum.network.response.PicsumResponseItem

/**
 * Converts [PicsumResponseItem] to [PicsumPicture]
 */
fun PicsumResponseItem.toDomainModel() = PicsumPicture(
    id = id,
    author = author,
    width = width,
    height = height,
    url = url,
    downloadUrl = downloadUrl
)

/**
 * Converts [PicsumPicture] to [MainPagerItemModel]
 */
fun PicsumPicture.toAdapterItem() = MainPagerItemModel(
    id = id,
    width = width,
    height = height,
    downloadUrl = downloadUrl,
)

/**
 * Recursively calculate image size by reducing it in 10% increments
 */
fun PicsumPicture.calculateSize(): PicsumPicture {
    return if (width > screenMetrics.width / 2) {
        copy(
            width = (width / 1.1).toInt(),
            height = (height / 1.1).toInt(),
        ).calculateSize()
    } else this
}

/**
 * Returns url based on the fields
 */
val PicsumPicture.calculatedUrl: String
    get() = "https://picsum.photos/id/$id/$width/$height"

/**
 * Converts [Float] to [BlurValue]
 */
fun Float.toBlurValue() = BlurValue(this.toInt())

/**
 * Animate view's visibility change. Supports on the fly animation reverse
 */
fun View.animateVisibility(visibility: Int, duration: Long = 200) {
    // Were we animating before? If so, what was the visibility?
    val endAnimVisibility = this.getTag(R.id.finalVisibility) as? Int
    val oldVisibility = endAnimVisibility ?: this.visibility
    if (oldVisibility == visibility) {
        // just let it finish any current animation.
        return
    }
    val isVisible = oldVisibility == View.VISIBLE
    val willBeVisible = visibility == View.VISIBLE
    this.visibility = View.VISIBLE
    var startAlpha = if (isVisible) 1f else 0f
    if (endAnimVisibility != null) {
        startAlpha = this.alpha
    }
    val endAlpha = if (willBeVisible) 1f else 0f

    // Now create an animator
    val alpha: ObjectAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, startAlpha, endAlpha)
    alpha.setAutoCancel(true)
    alpha.duration = duration
    alpha.addListener(object : AnimatorListenerAdapter() {
        private var isCanceled = false
        override fun onAnimationStart(anim: Animator) {
            this@animateVisibility.setTag(R.id.finalVisibility, visibility)
        }

        override fun onAnimationCancel(anim: Animator) {
            isCanceled = true
        }

        override fun onAnimationEnd(anim: Animator) {
            this@animateVisibility.setTag(R.id.finalVisibility, null)
            if (!isCanceled) {
                this@animateVisibility.alpha = 1f
                this@animateVisibility.visibility = visibility
            }
        }
    })
    alpha.start()
}


