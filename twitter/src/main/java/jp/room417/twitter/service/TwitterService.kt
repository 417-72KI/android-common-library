package jp.room417.twitter.service

import jp.room417.twitter4k.Twitter
import twitter4j.auth.AccessToken

interface TwitterService {
    val twitter: Twitter
    val origin: twitter4j.Twitter
    val hasAccessToken: Boolean

    fun storeAccessToken(accessToken: AccessToken)
    fun clearAccessToken()
}
