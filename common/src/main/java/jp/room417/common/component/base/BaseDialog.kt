package jp.room417.common.component.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

open class BaseDialog : DialogFragment() {
    @JvmOverloads
    fun show(fragment: Fragment, tag: String? = null) {
        fragment.fragmentManager?.beginTransaction()?.apply {
            add(this@BaseDialog, tag)
            commitAllowingStateLoss()
        }
    }

    @JvmOverloads
    fun show(activity: FragmentActivity, tag: String? = null) {
        activity.supportFragmentManager.beginTransaction().apply {
            add(this@BaseDialog, tag)
            commitAllowingStateLoss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugLog("onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debugLog("onViewCreated")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        debugLog("onAttach")
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

    override fun onDetach() {
        super.onDetach()
        debugLog("onDetach")
    }

    protected fun debugLog(msg: String) {
        Log.d(javaClass.simpleName, msg)
    }
}
