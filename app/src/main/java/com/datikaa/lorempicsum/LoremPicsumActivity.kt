package com.datikaa.lorempicsum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.datikaa.lorempicsum.feature.main_pager.MainPagerFragment

class LoremPicsumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainPagerFragment.newInstance())
                .commitNow()
        }
    }
}