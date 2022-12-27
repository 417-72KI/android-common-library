package jp.room417.twitter.service

import android.content.Context
import jp.room417.common.extension.letWith
import jp.room417.common.util.PrefSys
import twitter4j.AccessToken
import twitter4j.OAuthAuthorization
import twitter4j.Twitter

internal class TwitterServiceImpl(
    context: Context,
    private val apiKey: String,
    private val secret: String
) : TwitterService {
    override val twitter: Twitter
        get() {
            val builder = Twitter.newBuilder()
                .oAuthConsumer(apiKey, secret)
            loadAccessToken()?.let {
                builder.oAuthAccessToken(it.first, it.second)
            }
            return builder.build()
        }

    override val auth: OAuthAuthorization
        get() = OAuthAuthorization.getInstance(apiKey, secret)

    private val prefSys = PrefSys(context)

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
    }

    private fun loadAccessToken(): Pair<String, String>? = prefSys.run {
        getPrefString(TOKEN)?.letWith(getPrefString(TOKEN_SECRET)) { token, tokenSecret ->
            Pair(token, tokenSecret)
        }
    }

    companion object {
        private const val TOKEN = "twitter_token"
        private const val TOKEN_SECRET = "twitter_token_secret"
    }
}
