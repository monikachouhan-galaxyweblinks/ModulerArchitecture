package com.gwl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gwl.navigation.features.FingerLockNavigation
import com.gwl.navigation.features.LoginNavigation
import com.gwl.navigation.features.PlayerNavigation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFingerPrint?.setOnClickListener { startFingerPrint() }

        btnFeed?.setOnClickListener { startLogin() }

        btnVideoPlayer?.setOnClickListener { startPlayerFeed() }
    }

    private fun startPlayerFeed() = PlayerNavigation.dynamicStart?.let { startActivity(it) }
    private fun startLogin() = LoginNavigation.dynamicStart?.let { startActivity(it) }
    private fun startFingerPrint() = FingerLockNavigation.dynamicStart?.let { startActivity(it) }

    companion object {
        private const val HOME = 100
    }
}
