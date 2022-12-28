package jp.room417.common.sampleApp.scene

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.room417.common.util.PrefSys
import jp.room417.twitter.service.TwitterService
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val twitterService: TwitterService
) : AndroidViewModel(application) {
    val prefSys = PrefSys(application)

    val hasAccessToken
        get() = twitterService.hasAccessToken

    val twitter
        get() = twitterService.twitter
}
