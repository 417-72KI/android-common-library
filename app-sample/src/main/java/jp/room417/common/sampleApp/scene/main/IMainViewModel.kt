package jp.room417.common.sampleApp.scene.main

import androidx.compose.runtime.State
import jp.room417.common.util.PrefSys

interface IMainViewModel {
    val prefSys: PrefSys
    val hasAccessToken: State<Boolean>

    val text: State<String>
    val isLoading: State<Boolean>
    val error: State<Exception?>

    fun onChangeText(newValue: String)
    fun onClearError()
    fun post(text: String)

    fun resetAuth()
}
