package jp.room417.common.sampleApp.scene

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.room417.common.component.base.debugLog
import jp.room417.common.sampleApp.BuildConfig
import jp.room417.common.sampleApp.scene.auth.TwitterOAuthActivity
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
                MainScreen(viewModel.hasAccessToken) {
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

@Composable
private fun MainScreen(hasAccessToken: Boolean, onAuthAction: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Center
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = spacedBy(8.dp)
        ) {
            if (hasAccessToken) {
                Greeting("Twitter")
            } else {
                Greeting("World")
                Button(onClick = onAuthAction) {
                    Text("Auth twitter")
                }
            }
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AndroidcommonlibraryTheme {
        MainScreen(hasAccessToken = true) {
        }
    }
}
