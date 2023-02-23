package jp.room417.twitter.component

import android.app.Activity
import android.content.Intent

class OAuthActivityBuilder<A : OAuthBaseActivity>(
    private val clazz: Class<A>,
    private val activity: Activity,
    private val callbackURL: String,
    private val apiKey: String,
    private val apiSecret: String,
) {
    fun buildIntent() = Intent(activity, clazz).apply {
        putExtra(IntentKey.CALLBACK_ACTIVITY, activity.javaClass.name)
        putExtra(IntentKey.CALLBACK_URL, callbackURL)
        putExtra(IntentKey.API_KEY, apiKey)
        putExtra(IntentKey.API_SECRET, apiSecret)
    }
}
