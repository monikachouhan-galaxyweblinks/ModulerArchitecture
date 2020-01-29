/*
 * Copyright (c) 2018 Nam Nguyen, nam@gwl.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gwl.playerfeed.basic

import android.net.Uri
import android.util.Log
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.gwl.MyApplication
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.model.MediaFeed
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.ExoPlayerViewHelper
import com.gwl.playerfeed.R
import com.gwl.toro.ToroPlayer
import com.gwl.toro.ToroPlayer.EventListener
import com.gwl.toro.ToroUtil.visibleAreaOffset
import com.gwl.toro.media.PlaybackInfo
import com.gwl.toro.widget.Container

/**
 * @author eneim (2018/01/23).
 */
internal class VideoFeedViewHolder(itemRowBind: ViewDataBinding) :
    BaseViewHolder<MediaFeed>(itemRowBind), ToroPlayer {

    companion object {
        const val defaultRatio = 100 * 165.78F / 360F // magic number.
    }

    private val playerFrame by lazy { itemView as AspectRatioFrameLayout }
    private val player = itemView.findViewById(R.id.playerView) as PlayerView
    private val title = itemView.findViewById(R.id.textTitle) as TextView
    private val description = itemView.findViewById(R.id.textDescription) as TextView
    private var helper: ExoPlayerViewHelper? = null
    private var videoUri: Uri? = null

    var listener: EventListener? = null
    var autoplay: Boolean = false
    override fun bind(
        data: MediaFeed,
        onItemClickListener: BaseAdapter.OnItemClickListener<MediaFeed>?
    ) {
        super.bind(data, onItemClickListener)
        itemRowBinding.setVariable(BR.item, data)
        videoUri = Uri.parse(data.videoUrl)
        //playerFrame.setAspectRatio(16/9f)
    }

    override fun getPlayerView() = player

    override fun getCurrentPlaybackInfo() = helper?.latestPlaybackInfo ?: PlaybackInfo()

    override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
        if (helper == null) helper = ExoPlayerViewHelper(
            this,
            videoUri!!,
            null,
            MyApplication.config!!
        )
        if (listener == null) {
            listener = object : EventListener {
                override fun onFirstFrameRendered() {
                    // status.text = "First frame rendered"
                }

                override fun onBuffering() {
                    // status.text = "Buffering"
                    Log.d("VideoFeedViewHolder", " initialize onBuffering")

                }

                override fun onPlaying() {
                    // status.text = "Playing"
                    Log.d("VideoFeedViewHolder", " initialize onPlaying")

                }

                override fun onPaused() {
                    // status.text = "Paused"
                    Log.d("VideoFeedViewHolder", " initialize onPaused")

                }

                override fun onCompleted() {
                    //status.text = "Completed"
                    Log.d("VideoFeedViewHolder", " initialize onCompleted")

                }

            }
            helper!!.addPlayerEventListener(listener!!)
        }
        helper!!.initialize(container, playbackInfo)
    }

    override fun play() {
        helper!!.play(autoplay)
    }

    override fun pause() {
        helper!!.pause()
    }

    override fun isPlaying() = helper?.isPlaying ?: false

    override fun release() {
        if (listener != null) {
            helper?.removePlayerEventListener(listener)
            listener = null
        }
        helper?.release()
        helper = null
    }

    override fun wantsToPlay() = visibleAreaOffset(this, itemView.parent) >= 0.65

    override fun getPlayerOrder() = adapterPosition

}