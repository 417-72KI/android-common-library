package jp.room417.common.sampleApp.scene

import android.os.Bundle
import jp.room417.common.component.base.BaseActivity
import jp.room417.common.sampleApp.R
import jp.room417.common.util.PrefSys

class MainActivity : BaseActivity(R.layout.main_activity) {
    private lateinit var prefSys: PrefSys

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefSys = PrefSys(this)

        debugLog("${prefSys.getPrefBoolean("foo")}")
    }
}
