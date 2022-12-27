package jp.room417.twitter.service

import twitter4j.AccessToken
import twitter4j.Twitter

interface TwitterService {
    val twitter: Twitter
    val hasAccessToken: Boolean

    fun storeAccessToken(accessToken: AccessToken)
    fun clearAccessToken()
}
