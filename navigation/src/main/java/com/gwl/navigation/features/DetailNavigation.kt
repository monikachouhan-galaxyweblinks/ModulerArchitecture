package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object DetailNavigation : DynamicFeature<Intent> {

    private const val VIDEO_DETAIL = "com.gwl.details.VideoDetailActivity"

    override val dynamicStart: Intent?
        get() = VIDEO_DETAIL.loadIntentOrNull()
}
