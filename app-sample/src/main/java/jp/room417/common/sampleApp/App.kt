package jp.room417.common.sampleApp

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onTerminate() {
        super.onTerminate()
        log("onTerminate")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        log("onConfigurationChanged: $newConfig")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        log("onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        log("onTrimMemory: $level")
    }

    // private
    private fun log(msg: String) {
        Log.d(javaClass.simpleName, msg)
    }
}
