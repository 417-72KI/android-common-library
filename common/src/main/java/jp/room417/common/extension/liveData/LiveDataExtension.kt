package jp.room417.common.extension.liveData

import androidx.lifecycle.LiveData

val LiveData<Boolean>.isTrue: Boolean
    get() = value == true
