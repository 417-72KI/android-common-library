package jp.room417.twitter.service

import android.content.Context
import jp.room417.common.extension.letWith
import jp.room417.common.util.PrefSys
import jp.room417.twitter4kt.Twitter
import jp.room417.twitter4kt.auth.OAuthAuthorization
import twitter4j.AccessToken

internal class TwitterServiceImpl(
    context: Context,
    private val apiKey: String,
    private val secret: String,
) : TwitterService {
    companion object {
        private const val TOKEN = "twitter_token"
        private const val TOKEN_SECRET = "twitter_token_secret"
    }

    override val twitter
        get() = loadAccessToken()?.let { token ->
            Twitter.Builder(
                consumerKey = apiKey,
                consumerSecret = secret,
            ).setOAuthAccessToken(token.first, token.second)
                .build()
        }

    override val oAuthAuthorization
        get() = OAuthAuthorization(consumerKey = apiKey, consumerSecret = secret)

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
}
