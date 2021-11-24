package com.datikaa.lorempicsum

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.datikaa.lorempicsum.databinding.ActivityLoremPicsumBinding
import com.datikaa.lorempicsum.feature.main_pager.MainPagerFragment

class LoremPicsumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoremPicsumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoremPicsumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}

data class ScreenMetrics(
    val height: Int,
    val width: Int,
)

fun DisplayMetrics.toScreenMetrics() = ScreenMetrics(
    height = heightPixels,
    width = widthPixels,
)