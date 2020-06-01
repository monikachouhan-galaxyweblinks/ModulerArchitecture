package com.gwl

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.gwl.cache.db.AppDatabase
import com.gwl.core.CoreApplication
import com.gwl.core.LoginManager
import com.gwl.navigation.Navigation
import com.gwl.networking.NetworkingApiApplication
import com.gwl.networking.client.server.NetworkAPI
import com.gwl.networking.client.server.NetworkAPIFactory

// * Created on 14/1/20.
/**
 * @author GWL
 */
class MyApplication : Application() {
    val networkAPI: NetworkAPI by lazy { NetworkAPIFactory.standardClient(this) }

    init {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        instance = this
    }

    //region - Companion function
    companion object {
        lateinit var instance: MyApplication
        val loginManager by lazy { LoginManager.getInstance(instance) }
        val database by lazy { AppDatabase.getInstance(instance) }
    }

    override fun onCreate() {
        super.onCreate()
        Navigation.PACKAGE_NAME = applicationInfo.packageName
        CoreApplication.init(this)
        NetworkingApiApplication.init(this)
    }
}