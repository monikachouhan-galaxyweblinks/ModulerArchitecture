package com.gwl.networking

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object NetworkingApiApplication {
    lateinit var context: Context
    fun init(context: Context) {
        if (!this::context.isInitialized) {
            this.context = context
        }
    }
}