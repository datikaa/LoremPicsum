package com.datikaa.lorempicsum.feature.picture_detail

@JvmInline
value class BlurValue(private val blurValue: Int) {

    init {
        // ensure that [blurValue] is in the range of possible values
        val possibleBlurRange = IntRange(1, 10)
        require(possibleBlurRange.contains(blurValue))
    }
}