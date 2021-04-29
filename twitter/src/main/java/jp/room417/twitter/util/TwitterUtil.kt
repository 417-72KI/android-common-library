package jp.room417.twitter.util

import android.content.Context
import jp.room417.common.extension.letWith
import jp.room417.common.util.PrefSys
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

@Deprecated("Use `TwitterService`", replaceWith = ReplaceWith("TwitterService"))
object TwitterUtil {
    private const val TOKEN = "twitter_token"
    private const val TOKEN_SECRET = "twitter_token_secret"

    /**
     * Twitterインスタンスを取得します。アクセストークンが保存されていれば自動的にセットします。
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getTwitterInstance(context: Context, apiKey: String, secret: String): Twitter {
        val factory = TwitterFactory()
        val twitter = factory.instance
        twitter.setOAuthConsumer(apiKey, secret)
        if (hasAccessToken(context)) {
            twitter.oAuthAccessToken = loadAccessToken(context)
        }
        return twitter
    }

    /**
     * アクセストークンをプリファレンスに保存します。
     *
     * @param context
     * @param accessToken
     */
    @JvmStatic
    fun storeAccessToken(context: Context, accessToken: AccessToken) {
        val p = PrefSys(context)
        p.setPrefString(TOKEN, accessToken.token)
        p.setPrefString(TOKEN_SECRET, accessToken.tokenSecret)
    }

    /**
     * アクセストークンをプリファレンスから読み込みます。
     *
     * @param context
     * @return
     */
    @JvmStatic
    private fun loadAccessToken(context: Context): AccessToken? = PrefSys(context).let {
        it.getPrefString(TOKEN)?.letWith(it.getPrefString(TOKEN_SECRET)) {
            token, tokenSecret ->
            AccessToken(token, tokenSecret)
        }
    }

    /**
     * アクセストークンが存在する場合はtrueを返します。
     *
     * @return
     */
    @JvmStatic
    fun hasAccessToken(context: Context): Boolean = loadAccessToken(context) != null

    /**
     * アクセストークンを削除します。
     */
    @JvmStatic
    fun clearAccessToken(context: Context) = PrefSys(context).run {
        removePref(TOKEN)
        removePref(TOKEN_SECRET)
    }
}
