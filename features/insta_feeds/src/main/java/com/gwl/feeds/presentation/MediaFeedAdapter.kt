package com.gwl.feeds.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gwl.core.BaseViewHolder
import com.gwl.feeds.R
import com.gwl.model.InstaFeed
import com.gwl.model.MediaType

// * Created on 20/1/20.
/**
 * @author GWL
 */
class MediaFeedAdapter : PagedListAdapter<InstaFeed, BaseViewHolder<InstaFeed>>(object :
    DiffUtil.ItemCallback<InstaFeed>() {
    override fun areItemsTheSame(oldItem: InstaFeed, newItem: InstaFeed): Boolean {
        Log.d("areItemsTheSame", "areItemsTheSame ----")

        val data = /*(oldItem::class == newItem::class &&*/ oldItem.id == newItem.id  //)
        Log.d("areItemsTheSame", "---areItemsTheSame $data")
        return data /*(oldItem::class == newItem::class && oldItem.id == newItem.id)*/
    }


    override fun areContentsTheSame(oldItem: InstaFeed, newItem: InstaFeed): Boolean {
        Log.d("areItemsTheSame", "areContentsTheSame --")

        val iss = (oldItem.equals(newItem))
        Log.d("areItemsTheSame", "areContentsTheSame $iss")
        return iss
    }
}) {

    companion object {
        val VIDEO_VIEW_TYPE = 0
        val IMAGE_VIEW_TYPE = 1
        val CAROSEL_VIEW_TYPE = 3
        val UNKNOWN_VIEW_TYPE = -1
    }

    //    var receivedFirstLoad = false
    var isAutoPlay: Boolean = false
    var itemClick: com.gwl.core.BaseAdapter.OnItemClickListener<InstaFeed>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<InstaFeed> {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_view_video, parent, false
        )
        val carouselView = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_view_carosel, parent, false
        )
        return when (viewType) {
            VIDEO_VIEW_TYPE -> VideoFeedViewHolder(view)
            CAROSEL_VIEW_TYPE -> CarouselViewHolder(carouselView)
            else -> ImageMediaFeedViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder<InstaFeed>, position: Int) {
        val feedItem = getItem(position)
        feedItem?.also {
            viewHolder.bind(it, itemClick)
            when (getItemViewType(position)) {
                VIDEO_VIEW_TYPE -> {
                    (viewHolder as VideoFeedViewHolder).autoplay =
                        if (feedItem.type.equals(MediaType.VIDEO.value, true)) isAutoPlay else false
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)?.type) {
            MediaType.VIDEO.value -> VIDEO_VIEW_TYPE
            MediaType.IMAGE.value -> IMAGE_VIEW_TYPE
            MediaType.CAROSEL.value -> CAROSEL_VIEW_TYPE
            else -> UNKNOWN_VIEW_TYPE
        }
    }
    // object MediaCallback :

}
