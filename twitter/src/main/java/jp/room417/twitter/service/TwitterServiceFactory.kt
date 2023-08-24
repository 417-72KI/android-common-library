package jp.room417.twitter.service

import android.content.Context

@Deprecated("Twitter v1.1 APIs are EOL and this library will be removed on next major version.")
abstract class TwitterServiceFactory {
    abstract fun createService(context: Context, apiKey: String, secret: String): TwitterService
}
