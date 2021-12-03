package jp.room417.twitter.component

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import jp.room417.common.component.base.BaseActivity
import jp.room417.twitter.R
import jp.room417.twitter.extension.isAuthorized
import jp.room417.twitter.service.DefaultTwitterServiceFactory
import jp.room417.twitter.service.TwitterService
import jp.room417.twitter.service.TwitterServiceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.Twitter
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

abstract class OAuthBaseActivity(
    private val factory: TwitterServiceFactory = DefaultTwitterServiceFactory()
) : BaseActivity() {
    private lateinit var callbackURL: String
    private lateinit var callbackActivity: Class<*>
    private lateinit var apiKey: String
    private lateinit var apiSecret: String

    private lateinit var twitterService: TwitterService
    private val twitter: Twitter
        get() = twitterService.twitter

    private var requestToken: RequestToken? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        setCallBackActivity()

        twitterService = factory.createService(this, apiKey, apiSecret)

        if (twitter.isAuthorized) {
            showToast(R.string.twitter_already_connected)
            callBack()
        } else {
            scope.launch {
                startAuthorize()
            }
        }
    }

    /**
     * 認証後の戻り先画面を設定する
     */
    private fun setCallBackActivity() {
        val extras = intent.extras ?: throw IllegalStateException("No extras set in intent.")
        val cls = extras[IntentKey.CALLBACK_ACTIVITY] as? Class<*>
        callbackActivity = if (cls != null && Activity::class.java.isAssignableFrom(cls)) {
            cls
        } else {
            throw IllegalStateException("No Activity set as callback. Intent for this activity must be created by `TwitterOAuthActivity.newIntent()`. ")
        }
        callbackURL = extras[IntentKey.CALLBACK_URL] as? String
            ?: throw IllegalStateException("No callback URL set. Intent for this activity must be created by `TwitterOAuthActivity.newIntent()`. ")
        apiKey = extras[IntentKey.API_KEY] as? String
            ?: throw IllegalStateException("No API key set. Intent for this activity must be created by `TwitterOAuthActivity.newIntent()`. ")
        apiSecret = extras[IntentKey.API_SECRET] as? String
            ?: throw IllegalStateException("No API secret set. Intent for this activity must be created by `TwitterOAuthActivity.newIntent()`. ")
    }

    /**
     * OAuth認証（厳密には認可）を開始します。
     */
    private suspend fun startAuthorize() {
        val url = withContext(Dispatchers.Default) {
            requestToken = twitter.getOAuthRequestToken(callbackURL)
            requestToken?.authenticationURL?.let { Uri.parse(it) }
        }
        withContext(Dispatchers.Main) {
            when (url) {
                null -> {
                    showToast(R.string.undefined_error)
                    callBack()
                }
                else -> startActivity(Intent(Intent.ACTION_VIEW, url))
            }
        }
    }

    public override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val verifier = intent.data?.let {
            when {
                it.toString().startsWith(callbackURL) -> it.getQueryParameter("oauth_verifier")
                else -> null
            }
        } ?: return callBack()

        scope.launch {
            val accessToken = withContext(Dispatchers.Default) {
                twitter.getOAuthAccessToken(requestToken, verifier)
            }
            withContext(Dispatchers.Main) {
                when (accessToken) {
                    null -> {
                        // 認証失敗。。。
                        showToast(R.string.auth_failed)
                    }
                    else -> {
                        // 認証成功！
                        showToast(R.string.auth_success)
                        successOAuth(accessToken)
                    }
                }
            }
        }
    }

    private fun successOAuth(accessToken: AccessToken) {
        twitterService.storeAccessToken(accessToken)
        callBack()
    }

    private fun callBack() {
        val intent = Intent(this, callbackActivity)
        startActivity(intent)
        finish()
    }
}
