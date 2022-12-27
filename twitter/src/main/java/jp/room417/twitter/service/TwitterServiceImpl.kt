package jp.room417.twitter.service

import android.content.Context
import jp.room417.common.extension.letWith
import jp.room417.common.util.PrefSys
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.AccessToken

internal class TwitterServiceImpl(
    context: Context,
    apiKey: String,
    secret: String
) : TwitterService {
    override val twitter: Twitter

    private val prefSys = PrefSys(context)

    init {
        val factory = TwitterFactory()
        twitter = factory.instance
        twitter.setOAuthConsumer(apiKey, secret)
        if (hasAccessToken) {
            twitter.oAuthAccessToken = loadAccessToken()
        }
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
        twitter.oAuthAccessToken = null
    }

    private fun loadAccessToken(): AccessToken? = prefSys.run {
        getPrefString(TOKEN)?.letWith(getPrefString(TOKEN_SECRET)) { token, tokenSecret ->
            AccessToken(token, tokenSecret)
        }
    }

    companion object {
        private const val TOKEN = "twitter_token"
        private const val TOKEN_SECRET = "twitter_token_secret"
    }
}
