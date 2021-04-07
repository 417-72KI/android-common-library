package jp.room417.common.component.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment {
    constructor() : super()

    @ContentView
    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugLog("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        debugLog("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
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
