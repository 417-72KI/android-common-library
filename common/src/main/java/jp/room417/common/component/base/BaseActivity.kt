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
        log("onCreate")
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    protected fun log(msg: String) {
        Log.d(javaClass.simpleName, msg)
    }
}
