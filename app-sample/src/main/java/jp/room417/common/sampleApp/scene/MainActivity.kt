package jp.room417.common.sampleApp.scene

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.room417.common.component.base.debugLog
import jp.room417.common.sampleApp.BuildConfig
import jp.room417.common.sampleApp.scene.auth.TwitterOAuthActivity
import jp.room417.common.sampleApp.scene.main.MainScreen
import jp.room417.common.sampleApp.scene.main.MainViewModel
import jp.room417.common.sampleApp.ui.theme.AndroidcommonlibraryTheme
import jp.room417.twitter.component.OAuthActivityBuilder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            debugLog("${viewModel.prefSys.getPrefBoolean("foo")}")
            debugLog("hasAccessToken: ${viewModel.hasAccessToken}")

            AndroidcommonlibraryTheme {
                MainScreen(viewModel) {
                    OAuthActivityBuilder(
                        TwitterOAuthActivity::class.java,
                        this,
                        BuildConfig.TWITTER_CALLBACK_URL,
                        BuildConfig.TWITTER_API_KEY,
                        BuildConfig.TWITTER_API_SECRET
                    ).buildIntent().let { startActivity(it) }
                }
            }
        }
    }
}

