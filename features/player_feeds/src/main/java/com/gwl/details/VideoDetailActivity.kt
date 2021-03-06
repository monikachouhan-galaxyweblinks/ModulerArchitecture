package com.gwl.details

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.model.ArticlesItem
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.R
import com.gwl.playerfeed.databinding.ActivityDetailBinding
import com.gwl.playerfeed.presentation.MediaFeedFragment
import kotlinx.android.synthetic.main.activity_detail.*

class VideoDetailActivity : BaseActivity<ActivityDetailBinding, VideoDetailViewModel>() {
    lateinit var item: ArticlesItem
    var fullscreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        getIntentData()
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        mDataBinding.setVariable(BR.item, item)
        initPlayerView()
        setupToolbar(item.title, true)
        fullScreen()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getIntentData() {
        item = intent.getParcelableExtra(MediaFeedFragment.DATA) as ArticlesItem
    }

    private fun initPlayerView() {
        val PlayerView1 = playerViewController as PlayerView
        val player = ExoPlayerFactory.newSimpleInstance(this)
        PlayerView1.player = player
        val uri = Uri.parse(item.sourceUrl)
        val mediaSource = buildMediaSource(uri)
        player.playWhenReady = true
        player.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        val mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
        val mediaSource1 = mediaSourceFactory.createMediaSource(uri)
        return ConcatenatingMediaSource(mediaSource1)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun getViewModel(): VideoDetailViewModel {
        return initViewModel {
            VideoDetailViewModel(item)
        }
    }

    fun fullScreen() {
        val fullscreenButton =
            playerViewController.findViewById(R.id.exo_fullscreen_icon) as ImageView
        fullscreenButton.setOnClickListener {
            fullscreen = if (fullscreen) {
                changeToPortrait()
                false
            } else {
                changeToLandscape()
                true
            }
        }
    }

    private fun changeToPortrait() {
        val attr = window.attributes
        val window = window
        window.attributes = attr
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        textTitle.visibility = View.VISIBLE
        textDescription.visibility = View.VISIBLE
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        showSystemUI()
    }

    private fun changeToLandscape() {
        val lp = window.attributes
        val window = window
        window.attributes = lp
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        textTitle.visibility = View.GONE
        textDescription.visibility = View.GONE
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}
