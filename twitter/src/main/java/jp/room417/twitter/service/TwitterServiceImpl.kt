package jp.room417.twitter.service

import android.content.Context
import jp.room417.common.extension.letWith
import jp.room417.common.util.PrefSys
import jp.room417.twitter4kt.Twitter
import twitter4j.auth.AccessToken

internal class TwitterServiceImpl(
    context: Context,
    apiKey: String,
    secret: String
) : TwitterService {
    companion object {
        private const val TOKEN = "twitter_token"
        private const val TOKEN_SECRET = "twitter_token_secret"
    }

    override val twitter: Twitter

    override val origin: twitter4j.Twitter
        get() = twitter.origin

    private val prefSys = PrefSys(context)

    init {
        twitter = Twitter.Builder()
            .setOAuthConsumer(apiKey, secret)
            .apply {
                loadAccessToken()?.let {
                    setOAuthAccessToken(it)
                }
            }
            .build()
    }

    override val hasAccessToken: Boolean
        get() = loadAccessToken() != null

    override fun storeAccessToken(accessToken: AccessToken) {
        prefSys.run {
            setPrefString(TOKEN, accessToken.token)
            setPrefString(TOKEN_SECRET, accessToken.tokenSecret)
        }
    }

    override fun clearAccessToken() {
        prefSys.apply {
            removePref(TOKEN)
            removePref(TOKEN_SECRET)
        }
        origin.oAuthAccessToken = null
    }

    private fun loadAccessToken(): AccessToken? = prefSys.run {
        getPrefString(TOKEN)?.letWith(getPrefString(TOKEN_SECRET)) { token, tokenSecret ->
            AccessToken(token, tokenSecret)
        }
    }
}
