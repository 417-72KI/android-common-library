package jp.room417.twitter.service

import twitter4j.Twitter
import twitter4j.auth.AccessToken

interface TwitterService {
    val twitter: Twitter
    val hasAccessToken: Boolean

    fun storeAccessToken(accessToken: AccessToken)
    fun clearAccessToken()
}
