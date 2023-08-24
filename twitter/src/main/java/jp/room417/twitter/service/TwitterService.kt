package jp.room417.twitter.service

import jp.room417.twitter4kt.Twitter
import jp.room417.twitter4kt.auth.OAuthAuthorization
import twitter4j.AccessToken

@Deprecated("Twitter v1.1 APIs are EOL and this library will no longer support.")
interface TwitterService {
    val twitter: Twitter?
    val oAuthAuthorization: OAuthAuthorization
    val hasAccessToken: Boolean

    fun storeAccessToken(accessToken: AccessToken)
    fun clearAccessToken()
}
