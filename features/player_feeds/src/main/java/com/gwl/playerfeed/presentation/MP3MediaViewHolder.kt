package com.gwl.playerfeed.presentation

import android.net.Uri
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.model.ArticlesItem
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.R

// * Created on 29/1/20.
/**
 * @author GWL
 */
class MP3MediaViewHolder(itemRowBind: ViewDataBinding) : VideoFeedViewHolder(itemRowBind) {
    override var autoplay: Boolean = false


    override fun bind(
        data: ArticlesItem,
        onItemClickListener: BaseAdapter.OnItemClickListener<ArticlesItem>?
    ) {
        itemRowBinding.setVariable(BR.itemClick, onItemClickListener)
        isPlaying.set(false)
        itemRowBinding.setVariable(BR.item, data)
        itemRowBinding.setVariable(BR.isPlaying, isPlaying)
        videoUri = Uri.parse(data.sourceUrl)
    }
}