package com.feed

import android.os.Bundle
import android.util.Log
import com.feed.databinding.ActivityFeedBinding
import com.gwl.MyApplication
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.networking.client.server.NetworkAPI
import com.networking.dispatchers.DispatcherProvider
import com.networking.dispatchers.DispatcherProviderImpl

class FeedActivity : BaseActivity<ActivityFeedBinding, FeedViewModel>() {
    val dispatcherProvider: DispatcherProvider = DispatcherProviderImpl()
    val networkAPI: NetworkAPI by lazy { MyApplication.instance.networkAPI }
    override fun getLayoutId(): Int {
        return R.layout.activity_feed
    }

    override fun getViewModel(): FeedViewModel =
        initViewModel { FeedViewModel(networkAPI) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)

        mViewModel.initPager().observe {
            mViewModel.adapter.submitList(it)
            mViewModel.isApiRunning.set(false)
        }
    }

}
