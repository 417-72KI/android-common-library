package jp.room417.common.sampleApp.scene.main

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.room417.common.util.PrefSys
import jp.room417.twitter.service.TwitterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.TwitterException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val twitterService: TwitterService
) : AndroidViewModel(application), IMainViewModel {
    override val prefSys = PrefSys(application)

    override val hasAccessToken
        get() = twitterService.hasAccessToken

    override val text = mutableStateOf("")
    override val isLoading = mutableStateOf(false)
    override val error = mutableStateOf<Exception?>(null)

    override fun onChangeText(newValue: String) {
        text.value = newValue
    }

    override fun onClearError() {
        error.value = null
    }

    override fun post(text: String) {
        viewModelScope.launch {
            isLoading.value = true
            withContext(Dispatchers.IO) {
                try {
                    twitterService.twitter.updateStatus(text)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            getApplication<Application>().applicationContext,
                            "Post Success!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: TwitterException) {
                    error.value = e
                } finally {
                    withContext(Dispatchers.Main) {
                        isLoading.value = false
                        onChangeText("")
                    }
                }
            }
        }
    }

    override fun resetAuth() {
        twitterService.clearAccessToken()
    }
}

// FIXME: Mock for Context
//class FakeMainViewModel : IMainViewModel {
//    override val prefSys: PrefSys
//        get() = PrefSys(LocalContext.current)
//    override val hasAccessToken: Boolean
//        get() = TODO("Not yet implemented")
//
//    override fun post(text: String) {
//        TODO("Not yet implemented")
//    }
//}
