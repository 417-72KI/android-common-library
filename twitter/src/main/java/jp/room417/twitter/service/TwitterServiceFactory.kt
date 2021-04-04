package jp.room417.twitter.service

import android.content.Context

class TwitterServiceFactory {
    fun createService(context: Context, apiKey: String, secret: String): TwitterService =
        TwitterServiceImpl(context, apiKey, secret)
}
