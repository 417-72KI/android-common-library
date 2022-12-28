package jp.room417.common.sampleApp.scene

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.room417.common.util.PrefSys
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    val prefSys = PrefSys(application)
}
