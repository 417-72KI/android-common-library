package jp.room417.twitter.extension

import twitter4j.OAuthAuthorization
import twitter4j.TwitterException

val OAuthAuthorization.isAuthorized: Boolean
    get() = try {
        oAuthAccessToken != null
    } catch (e: Exception) {
        when (e) {
            is TwitterException, is IllegalStateException -> false
            else -> throw e
        }
    }
