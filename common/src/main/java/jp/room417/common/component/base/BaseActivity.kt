package jp.room417.common.component.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes

open class BaseActivity : AppCompatActivity {
    constructor(): super()
    @ContentView constructor(@LayoutRes layoutId: Int) : super(layoutId)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugLog("onCreate")
    }

    override fun onStart() {
        super.onStart()
        debugLog("onStart")
    }

    override fun onResume() {
        super.onResume()
        debugLog("onResume")
    }

    override fun onPause() {
        super.onPause()
        debugLog("onPause")
    }

    override fun onStop() {
        super.onStop()
        debugLog("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        debugLog("onDestroy")
    }

    protected fun debugLog(msg: String) {
        Log.d(javaClass.simpleName, msg)
    }
}
