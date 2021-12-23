package jp.room417.twitter.service

import android.content.Context

abstract class TwitterServiceFactory {
    abstract fun createService(context: Context, apiKey: String, secret: String): TwitterService
}
